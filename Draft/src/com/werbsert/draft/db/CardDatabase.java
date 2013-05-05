package com.werbsert.draft.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.werbsert.draft.CardFactory;
import com.werbsert.draft.model.Card;
import com.werbsert.draft.model.CardCollection;
import com.werbsert.draft.model.CardRarity;
import com.werbsert.draft.model.CardSet;

/**
 * CardDao manages conversion of database objects to and from Java objects, and
 * performs CRUD operations with the t_card table of the database.
 */
public class CardDatabase {
	
	//Singleton instance
	private static CardDatabase s_instance = null;
	
	private SQLiteDatabase m_database;
	private CardDatabaseHelper m_cardDatabaseHelper;
	private String[] allColumns = { CardDatabaseHelper.COLUMN_ID,
			CardDatabaseHelper.COLUMN_NAME, 
			CardDatabaseHelper.COLUMN_MANA,
			CardDatabaseHelper.COLUMN_CMC, 
			CardDatabaseHelper.COLUMN_TYPE,
			CardDatabaseHelper.COLUMN_TEXT, 
			CardDatabaseHelper.COLUMN_FLAVOR,
			CardDatabaseHelper.COLUMN_POWER,
			CardDatabaseHelper.COLUMN_TOUGHNESS,
			CardDatabaseHelper.COLUMN_SET,
			CardDatabaseHelper.COLUMN_RARITY, 
			CardDatabaseHelper.COLUMN_NUMBER,
			CardDatabaseHelper.COLUMN_ARTIST,
			CardDatabaseHelper.COLUMN_MULTIVERSE_ID,
			CardDatabaseHelper.COLUMN_IMAGE_URL };

	/**
	 * Constructor.  Do stuff that constructors do.
	 * 
	 * @param context
	 * @param cursorFactory
	 */
	private CardDatabase() {
	}
	
	//TODO: Figure out a better way of passing context to this class.
	/**
	 * 
	 * @param context the application context the SQLite db will reference.  Will only be read
	 * 			if the CardDatabase has not already been instantiated.
	 */
	public static CardDatabase getInstance() {
		return (s_instance != null)? s_instance : (s_instance = new CardDatabase());
	}
	
	public void init(Context context) {
		m_cardDatabaseHelper = new CardDatabaseHelper(context, CardDatabaseHelper.DATABASE_NAME, null, CardDatabaseHelper.DATABASE_VERSION);
	}

	/**
	 * Opens the card database.
	 * 
	 * @throws SQLException if database cannot be opened.
	 */
	public void open() throws SQLException {
		m_database = m_cardDatabaseHelper.getWritableDatabase();
	}

	public void close() {
		m_cardDatabaseHelper.close();
	}

	/**
	 * Create a card in the database with the given values.
	 * TODO: This function should not exist publicly.  Should only be run in CardDatabaseHelper.onCreate.
	 * 		 Figure out how to better place this logic.
	 * 		 Maybe we can make the 'allColumns' variable public, write this function as a member
	 * 		 of CardDatabasehelper and call in onCreate.
	 */
	public Card createCard(SQLiteDatabase db, String name, String mana, Integer cmc, String text,
			String type, String flavor, Integer power, Integer toughness,
			CardSet set, CardRarity rarity, Integer number,
			String artist, Integer multiverseId, String imageUrl) {
		
		ContentValues values = new ContentValues();
		values.put(CardDatabaseHelper.COLUMN_NAME, name);
		values.put(CardDatabaseHelper.COLUMN_MANA, mana);
		values.put(CardDatabaseHelper.COLUMN_CMC, cmc);
		values.put(CardDatabaseHelper.COLUMN_TYPE, type);
		values.put(CardDatabaseHelper.COLUMN_TEXT, text);
		values.put(CardDatabaseHelper.COLUMN_FLAVOR, flavor);
		values.put(CardDatabaseHelper.COLUMN_POWER, power);
		values.put(CardDatabaseHelper.COLUMN_TOUGHNESS, toughness);
		values.put(CardDatabaseHelper.COLUMN_SET, set.getShortName());
		values.put(CardDatabaseHelper.COLUMN_RARITY, rarity.getId());
		values.put(CardDatabaseHelper.COLUMN_NUMBER, number);
		values.put(CardDatabaseHelper.COLUMN_ARTIST, artist);
		values.put(CardDatabaseHelper.COLUMN_MULTIVERSE_ID, multiverseId);
		values.put(CardDatabaseHelper.COLUMN_IMAGE_URL, imageUrl);

		long insertId = db.insert(CardDatabaseHelper.TABLE_CARD, null,
				values);
		Cursor cursor = db.query(CardDatabaseHelper.TABLE_CARD,
				allColumns, CardDatabaseHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Card newCard = cursorToCard(cursor);
		cursor.close();
		return newCard;
	}
	
	public void deleteCard(Card card) {
		long id = card.getId();
		System.out.println("Comment deleted with id: " + id);
		m_database.delete(CardDatabaseHelper.TABLE_CARD, 
			CardDatabaseHelper.COLUMN_ID + " = " + id, 
			null);
	}

	public CardCollection getAllCards() {
		Cursor cursor = m_database.query(CardDatabaseHelper.TABLE_CARD,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		CardCollection cards = new CardCollection();
		while (!cursor.isAfterLast()) {
			Card card = cursorToCard(cursor);
			cards.addCard(card);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return cards;
	}

	/**
	 * Get a card by name and set (which should return 0 or 1 results, but
	 * I'm implementing this more generically in case, eg, a set has two of
	 * the same basic land printed or something).
	 * 
	 * @param cardName name of the card
	 * @param cardSet the desired set
	 * @return
	 */
	public CardCollection getCardByNameAndSet(String cardName, CardSet cardSet) {
		
		//Build our query
		String args[] = {cardName, cardSet.getShortName()};
		Cursor cursor = m_database.query(CardDatabaseHelper.TABLE_CARD, 
				allColumns, 
				CardDatabaseHelper.COLUMN_NAME + " = '?' and " +
					CardDatabaseHelper.COLUMN_SET + " = '?'", 
				args,
				null,
				null,
				null);
		
		//Pull cards
		CardCollection cards = new CardCollection();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Card card = cursorToCard(cursor);
			cards.addCard(card);
			cursor.moveToNext();
		}
		
		//Clean up and return
		cursor.close();
		return cards;
	}
	
	/**
	 * Get all the cards in a given set.
	 * @param cardSet the desired set
	 * @return
	 */
	public CardCollection getCardsBySet(CardSet cardSet) {
		
		//Build our query
		//String args[] = {cardSet.getShortName()};
		Cursor cursor = m_database.query(CardDatabaseHelper.TABLE_CARD, 
				allColumns, 
				CardDatabaseHelper.COLUMN_SET + " = '" + cardSet.getShortName() + "'", 
				null, 
				null, 
				null, 
				null);
		
		//Pull cards
		CardCollection cards = new CardCollection();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Card card = cursorToCard(cursor);
			cards.addCard(card);
			cursor.moveToNext();
		}
		
		//Close cursor and run with our money
		cursor.close();
		return cards;
	}
		
	private Card cursorToCard(Cursor cursor) {
		
		//Pull card values from table row
		int index = 0;
		Long id = cursor.getLong(index);
		String name = cursor.getString(++index);
		String mana = cursor.getString(++index);
		Integer cmc = cursor.getInt(++index);
		String type = cursor.getString(++index);
		String text = cursor.getString(++index);
		String flavor = cursor.getString(++index);
		Integer power = cursor.getInt(++index);
		Integer toughness = cursor.getInt(++index);
		CardSet set = CardSet.getSet(cursor.getString(++index));
		CardRarity rarity = CardRarity.getRarity(cursor.getString(++index));
		Integer number = cursor.getInt(++index);
		String artist = cursor.getString(++index);
		Integer multiverseId = cursor.getInt(++index);
		String imageUrl = cursor.getString(++index);
		
		//Construct and return card
		return CardFactory.getInstance().newCard(id, name, mana, cmc, type, text, flavor, power, toughness, set, rarity, number, artist, multiverseId, imageUrl);
	}
}

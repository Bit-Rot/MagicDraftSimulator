package com.werbsert.testdraftset.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.werbsert.draftcommon.db.DraftContract;
import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardRarity;
import com.werbsert.draftcommon.model.CardSet;

/**
 * TODO: Expand upon this class, I think this is supposed to encompass other objects, such as draft histories, etc
 * CardDatabase provides direct access to a SQL database.
 */
public class CardTable {

	public static final String DATABASE_NAME = "com.werbsert.testdraftset.card";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_CARD = "t_card";
	
	private static final String DATABASE_CREATE = "create table " + TABLE_CARD + "(" +
		DraftContract.Card.COLUMN_ID + " integer primary key autoincrement, " +
		DraftContract.Card.COLUMN_NAME + " text not null, " +
		DraftContract.Card.COLUMN_MANA + " text, " +
		DraftContract.Card.COLUMN_CMC + " text, " +
		DraftContract.Card.COLUMN_TYPE + " text not null, " +
		DraftContract.Card.COLUMN_TEXT + " text, " +
		DraftContract.Card.COLUMN_FLAVOR + " text, " +
		DraftContract.Card.COLUMN_POWER + " text, " +
		DraftContract.Card.COLUMN_TOUGHNESS + " text, " +
		DraftContract.Card.COLUMN_SET + " text not null, " +
		DraftContract.Card.COLUMN_RARITY + " text not null, " +
		DraftContract.Card.COLUMN_NUMBER + " text not null, " +
		DraftContract.Card.COLUMN_ARTIST + " text not null, " +
		DraftContract.Card.COLUMN_MULTIVERSE_ID + " text not null, " +
		DraftContract.Card.COLUMN_IMAGE_URL + " text not null);";

	/**
	 * Create a card in the database with the given values.
	 */
	private static Card createCard(SQLiteDatabase db, String name, String mana, Integer cmc, String text,
			String type, String flavor, Integer power, Integer toughness,
			CardSet set, CardRarity rarity, Integer number,
			String artist, Integer multiverseId, String imageUrl) {
		
		ContentValues values = new ContentValues();
		values.put(DraftContract.Card.COLUMN_NAME, name);
		values.put(DraftContract.Card.COLUMN_MANA, mana);
		values.put(DraftContract.Card.COLUMN_CMC, cmc);
		values.put(DraftContract.Card.COLUMN_TYPE, type);
		values.put(DraftContract.Card.COLUMN_TEXT, text);
		values.put(DraftContract.Card.COLUMN_FLAVOR, flavor);
		values.put(DraftContract.Card.COLUMN_POWER, power);
		values.put(DraftContract.Card.COLUMN_TOUGHNESS, toughness);
		values.put(DraftContract.Card.COLUMN_SET, set.getShortName());
		values.put(DraftContract.Card.COLUMN_RARITY, rarity.getId());
		values.put(DraftContract.Card.COLUMN_NUMBER, number);
		values.put(DraftContract.Card.COLUMN_ARTIST, artist);
		values.put(DraftContract.Card.COLUMN_MULTIVERSE_ID, multiverseId);
		values.put(DraftContract.Card.COLUMN_IMAGE_URL, imageUrl);

		long insertId = db.insert(TABLE_CARD, null,
				values);
		Cursor cursor = db.query(TABLE_CARD,
				DraftContract.Card.ALL_COLUMNS, DraftContract.Card.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Card newCard = cursorToCard(cursor);
		cursor.close();
		return newCard;
	}
		
	private static Card cursorToCard(Cursor cursor) {
		
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
		return new Card(id, name, mana, cmc, type, text, flavor, power, toughness, set, rarity, number, artist, multiverseId, imageUrl);
	}
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		
		//Hardcode some card DB dumps like a pro.
		createCard(database,
				"Abrupt Decay",
				"{B}{G}",
				2,
				"Abrupt Decay can't be countered by spells or abilities.\nDestroy target nonland permanent with converted mana cost 3 or less.",
				"Instant",
				"The Izzet quickly suspended their policy of lifetime guarantees.",
				null,
				null,
				CardSet.RTR,
				CardRarity.RARE,
				141,
				"Svetlin Velinov",
				253561,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253561&type=card");
		createCard(database,
				"Aerial Predation", 
				"2{G}", 
				3, 
				"Destroy target creature with flying. You gain 2 life.",
				"Instant", 
				"In the towering trees of the Samok Stand and the predators that guard them, the might of the Ravnican wild has returned.", 
				null, 
				null, 
				CardSet.RTR, 
				CardRarity.COMMON, 
				113, 
				"BD", 
				289227,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=289227&type=card");
		createCard(database,
				"Angel of Serenity", 
				"4{W}{W}{W}", 
				7, 
				"Flying\nWhen Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.\nWhen Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.", 
				"Creature - Angel", 
				null, 
				5, 
				6, 
				CardSet.RTR,
				CardRarity.MYTHIC,
				1,
				"Aleksi Birclot",
				253627,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253627&type=card");
		createCard(database,
				"Annihilating Fire", 
				"1{R}{R}", 
				3, 
				"Annihilating Fire deals 3 damage to target creature or player. If a creature dealt damage this way would die this turn, exile it instead.", 
				"Instant", 
				"\"The most impressive performances can be done only once.\"\n-Mote, Rakdos madcap",
				null, 
				null, 
				CardSet.RTR,  
				CardRarity.COMMON, 
				85, 
				"Clint Cearley", 
				270801,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270801&type=card");
		createCard(database,
				"Aquus Steed", 
				"3{U}", 
				4, 
				null, 
				"Creature - Beast", 
				"In water, it's as graceful as a dolphin. On land, it darts and jerks so unpredictably that few can ride it for long.", 
				1, 
				3, 
				CardSet.RTR,
				CardRarity.UNCOMMON, 
				29, 
				"Warren Mahy", 
				270361,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270361&type=card");
		createCard(database,
				"Archon of the Triumvirate",
				"5{W}{U}",
				7,
				"Flying\nWhenever Archon of the Triumvirate attacks, detain up to two target nonland permanents your opponents control. (Until your next turn, those permanents can't attack or block and their activated abilities can't be activated.)",
				"Creature - Archon",
				null,
				4,
				5,
				CardSet.RTR,
				CardRarity.RARE,
				142,
				"David Rapoza",
				253603,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253603&type=card");
		createCard(database,
				"Archweaver",
				"5{G}{G}",
				7,
				null,
				"Creature - Spider",
				"The silk of the archweavers adds structural integrity to otherwise unstable Izzet building sites.",
				5,
				5,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				114,
				"Jason Felix",
				253601,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253601&type=card");
		createCard(database,
				"Armada Wurm",
				"2{G}{G}{W}{W}",
				6,
				"Trample\nWhen Armada Wurm enters the battlefield, put a 5/5 green Wurm creature token with trample onto the battlefield.",
				"Creature - Wurm",
				null,
				5,
				5,
				CardSet.RTR,
				CardRarity.MYTHIC,
				143,
				"Volkan Baga",
				253587,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253587&type=card");
		createCard(database,
				"Armory Guard",
				"3{W}{W}",
				4,
				"Armory Guard has vigilance as long as you control a Gate.",
				"Creature - Giant Soldier",
				"The Dimir agents watched from the shadows. \"Eight hours, and I've yet to see him blink,\" Nefara hissed. \"I suggest we find another way in.\"",
				2,
				5,
				CardSet.RTR,
				CardRarity.COMMON,
				2,
				"Karl Kopinski",
				253570,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253570&type=card");
		createCard(database,
				"Arrest",
				"2{W}",
				3,
				"Target creature gets +4/-4 until end of turn.",
				"Instant",
				"\"Finally, a weapon the Boros can't confiscate!\"\n-Juri, proprietor of the Juri Revue",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				144,
				"Raymond Swanland",
				253579,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253579&type=card");
		createCard(database,
				"Avenging Arrow", 
				"2{W}",
				3,
				"Destroy target creature that dealt damage this turn.",
				"Instant",
				"\"Forgive the theft. Punish the deception.\"\n-Alcarus, Selesnya archer",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				4,
				"James Ryman",
				265410,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=265410&type=card");
		createCard(database,
				"Axebane Guardian",
				"2{G}",
				3,
				"Defender\n{Tap}: Add X mana in any combination of colors to your mana pool, where X is the number of creatures with defender you control.",
				"Creature - Human Druid",
				"Only a worthy few are permitted within the sentient forest known as Axebane.",
				0,
				3,
				CardSet.RTR,
				CardRarity.COMMON,
				115,
				"Slawomir Maniak",
				253556,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253556&type=card");
		createCard(database,
				"Axebane Stag",
				"6{G}",
				7,
				null,
				"Creature - Elk",
				"\"When the spires have burned and the cobblestones are dust, he will take his rightful place as king of the wilds.\"\n-Kirce, Axebane guardian",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				116,
				"Martina Pilcerova",
				265383,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=265383&type=card");
		createCard(database,
				"Azorius Arrester",
				"1{W}",
				2,
				"When Azorius Arrester enters the battlefield, detain target creature an opponent controls. (Until your next turn, that creature can't attack or block and its activated abilities can't be activated.)",
				"Creature - Human Soldier",
				"\"You have the right to remain silent. Mostly because I tire of your excuses.\"",
				2,
				1,
				CardSet.RTR,
				CardRarity.COMMON,
				5,
				"Wayn Reynolds",
				270972,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270972&type=card");
		createCard(database,
				"Azorius Guildgate",
				null,
				0,
				"Azorius Guildgate enters the battlefield tapped.\n{Tap}: Add {W} or {U} to your mana pool.",
				"Land - Gate",
				"Enter the Senate, the seat of justice and the foundation of Ravnican society.",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				237,
				"Drew Baker",
				270966,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270966&type=card");
		createCard(database,
				"Batterhorn",
				"4{R}",
				5,
				"When Batterhorn enters the battlefield, you may destroy target artifact.",
				"Creature - Beast",
				"Novice shopkeeps spend hours deciding how best to display their wares. Veterans focus on portability.",
				4,
				3,
				CardSet.RTR,
				CardRarity.COMMON,
				87,
				"Dave Kendall",
				265399,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=265399&type=card");
		createCard(database,
				"Bellows Lizard",
				"{R}",
				1,
				"{1}{R}: Bellows Lizard gets +1/+0 until end of turn.",
				"Creature - Lizard",
				"As the price of wood and coal rose, smiths found creative ways to keep their forges burning.",
				1,
				1,
				CardSet.RTR,
				CardRarity.COMMON,
				88,
				"Jack Wang",
				289214,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=289214&type=card");
	}

	//TODO: Integrate switch statement for DB Upgrades
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(CardTable.class.getName(), "Upgrading database from version "
		        + oldVersion + " to " + newVersion
		        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
	    onCreate(database);
	}
}

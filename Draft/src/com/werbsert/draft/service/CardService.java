package com.werbsert.draft.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.werbsert.draft.DraftSimulatorApplication;
import com.werbsert.draftcommon.db.DraftContract;
import com.werbsert.draftcommon.log.DebugLog;
import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardRarity;
import com.werbsert.draftcommon.model.CardSet;

public class CardService {
	
	private static CardService s_instance = null;
	
	public static CardService getInstance() {
		if (s_instance == null) {
			s_instance = new CardService();
		}
		return s_instance;
	}
	
	public void createCard(Card card) {
		
	}
	
	public Card getCard(long id) {
		return null;
	}
	
	public List<Card> getCardsBySet(CardSet set) {
		
		//Check for stupid input from stupid people
		if (null == set) {
			String errorMessage = "Argument \"set\" is null";
			DebugLog.log(errorMessage);
			throw new InvalidParameterException(errorMessage);
		}
		
		//Map set --> Database URI
		Uri uri;
		switch (set) {
		case M13:
			uri = DraftContract.TestDraftSet.CARD_URI;
			break;
		case RTR:
			uri = DraftContract.TestDraftSet.CARD_URI;
			break;
		default:
			String errorMessage = "Set \"" + set.getLongName() + "\" has no corresponding card database";
			DebugLog.log(errorMessage);
			throw new InvalidParameterException(errorMessage);
		}
		
		//Return the cards in the set
		return getCards(uri, null, null);
	}
	
	public List<Card> getCards(Uri uri, String selection, String[] selectionArgs) {
	
        Context context = DraftSimulatorApplication.getContext();
        if (context == null) {
        	String errorMessage = "Context not defined";
        	DebugLog.log(errorMessage);
        	throw new IllegalStateException(errorMessage);
        }
        
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, DraftContract.Card.ALL_COLUMNS, selection, selectionArgs, null);
        
        List<Card> cardList = new ArrayList<Card>();
        if (cursor != null && cursor.getCount() > 0) {
        	while (cursor.moveToNext()) {
        		Card card = new Card();
        		card.setId(cursor.getLong(DraftContract.Card.Column.ID.ordinal()));
    			card.setName(cursor.getString(DraftContract.Card.Column.NAME.ordinal()));
    			card.setMana(cursor.getString(DraftContract.Card.Column.MANA.ordinal()));
    			card.setCmc(cursor.getInt(DraftContract.Card.Column.CMC.ordinal()));
    			card.setType(cursor.getString(DraftContract.Card.Column.TYPE.ordinal()));
    			card.setText(cursor.getString(DraftContract.Card.Column.TEXT.ordinal()));
    			card.setFlavor(cursor.getString(DraftContract.Card.Column.FLAVOR.ordinal()));
    			card.setPower(cursor.getInt(DraftContract.Card.Column.POWER.ordinal()));
    			card.setToughness(cursor.getInt(DraftContract.Card.Column.TOUGHNESS.ordinal()));
    			card.setSet(CardSet.getSet(cursor.getString(DraftContract.Card.Column.SET.ordinal())));
    			card.setRarity(CardRarity.getRarity(cursor.getString(DraftContract.Card.Column.RARITY.ordinal())));
    			card.setNumber(cursor.getInt(DraftContract.Card.Column.NUMBER.ordinal()));
    			card.setArtist(cursor.getString(DraftContract.Card.Column.ARTIST.ordinal()));
    			card.setMultiverseId(cursor.getInt(DraftContract.Card.Column.MULTIVERSE_ID.ordinal()));
    			card.setImageUrl(cursor.getString(DraftContract.Card.Column.IMAGE_URL.ordinal()));
    			cardList.add(card);
        	}
        }
        
        return cardList;
	}
	
	public void updateCard(Card card) {
		
	}
	
	public void deleteCard(long id) {
		
	}
}

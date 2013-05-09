package com.werbsert.draft;

import java.util.HashMap;

import com.werbsert.draft.model.CardCollection;

import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardRarity;
import com.werbsert.draftcommon.model.CardSet;

/**
 * TODO: I think I wrote this class.  Not sure if valid any more.  Might want to look into trashing this, and pull cards directly from
 * the db classes instead.
 * 
 * @author BitRot
 */
public class CardFactory {
	
	//singleton class
	private static CardFactory s_instance = null;
	
	//Private properties
	private HashMap<Long, Card> m_cardCache;
	
	//private constructor
	private CardFactory() {
		m_cardCache = new HashMap<Long, Card>();
	}
	
	//public accessor
	public static CardFactory getInstance() {
		if (s_instance == null){
			s_instance = new CardFactory();
		}
		return s_instance;
	}
	
	public Card newCard(long id, 
			String name, 
			String mana, 
			Integer cmc, 
			String type, 
			String text, 
			String flavor, 
			Integer power, 
			Integer toughness, 
			CardSet set, 
			CardRarity rarity, 
			Integer number, 
			String artist, 
			Integer multiverseId,
			String imageUrl) {
		Card card = m_cardCache.get(id);
		if (card == null) {
			card = new Card(id, name, mana, cmc, type, text, flavor, power, toughness, set, rarity, number, artist, multiverseId, imageUrl);
			m_cardCache.put(card.getId(), card);
		}
		return card;
	}
	
	/*
	public Card getCard(CardSet set, int cNumber){
		return ((CardSet)cardSets.get(set)).getCard(cNumber);
	}
	*/
	public CardCollection getBoosterPack(CardSet set) {
		//TODO: Have this not be here, and implemented and stuff.
		return null;
		//return getCardDatabase().getCardSet(CardSet.RTR).getBoosterPack();
	}
}

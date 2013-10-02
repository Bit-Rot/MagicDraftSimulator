package com.werbsert.draft.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardRarity;

/**
 * Note that we may want to explore the ConcurrentSkipListSet class for the back-end data structure used
 * by CardCollection.  This would provide an efficient means of synchronizing access to the collection of
 * cards, moreso than simply synchronizing access to each method of the class.  However, in doing so, we 
 * must undertake additional work in ensuring order is preserved when displaying collections of cards and
 * that after deserializing cards between activities.
 * 
 * For now we'll just wrap all the methods with 'synchronized' to ensure thread safety and to rule out
 * any potential race conditions cause by concurrent access to a CardCollection.
 */
public class CardCollection {

	public List<Card> cards;
	
	public CardCollection(){
		cards = new ArrayList<Card>();
	}
	
	public CardCollection(Collection<Card> values) {
		cards = new ArrayList<Card>(values);
	}

	synchronized public void addCard(Card c){
		cards.add(c);
	}
	
	synchronized public List<Card> getCards(){
		return cards;
	}
	
	synchronized public Card getCard(int i ){
		return cards.get(i);
	}

	synchronized public Card removeCard(Card cardToRemove){
		Card removedCard = null;
		for(Card card: cards){
			if(card.equals(cardToRemove)){
				cards.remove(card);
				removedCard = card;
				break;
			}
		}
		return removedCard;
	}
	
	synchronized public List<Card> getCardsByRarity(CardRarity rarity){
		List<Card> cardsToReturn = new Vector<Card>();
		for(Card c: cards){
			if (c.getRarity() == rarity){
				cardsToReturn.add(c);
			}
		}
		return cardsToReturn;
	}

	synchronized public int getSize() {
		return this.cards.size();
	}
}

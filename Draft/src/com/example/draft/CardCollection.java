package com.example.draft;

import java.util.Collection;
import java.util.Vector;

public class CardCollection {
	
	public Vector<Card> cards;
	
	public CardCollection(){
		cards = new Vector<Card>();
	}
	
	public CardCollection(Collection<Card> values) {
		cards = new Vector<Card>(values);
	}

	public void addCard(Card c){
		cards.add(c);
	}
	
	public Vector<Card> getCards(){
		return cards;
	}
	
	public Card getCard(int i ){
		return cards.get(i);
	}
	
	public Vector<Card> getCardsByRarity(Card.Rarity rarity){
		Vector<Card> cardsToReturn = new Vector<Card>();
		for(Card c: cards){
			if (c.getRarity() == rarity){
				cardsToReturn.add(c);
			}
		}
		return cardsToReturn;
	}

	public int getSize() {
		return this.cards.size();
	}

}

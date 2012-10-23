package com.MagicDraft.CardDatabase;

import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

public class CardCollection implements Serializable{
	
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
	

	public void removeCard(Card cardToRemove){
		for(Card card: cards){
			if(card.equals(cardToRemove)){
				cards.remove(card);
				return;
			}
		}
	}
	
	
	public CardCollection getCardsByRarity(Card.Rarity rarity){
		CardCollection cardsToReturn = new CardCollection();
		for(Card c: cards){
			if (c.getRarity() == rarity){
				cardsToReturn.addCard(c);
			}
		}
		return cardsToReturn;
	}

	public int getSize() {
		return this.cards.size();
	}
	
	
	public void sortCards(){
		
	}

}

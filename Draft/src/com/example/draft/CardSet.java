package com.example.draft;

import java.util.HashMap;

public class CardSet {
	
	private HashMap<Integer, Card> cards;	
	private String name;
	
	public CardSet(){
		this.cards = new HashMap<Integer, Card>();
	}		

	public String getName(){
		return this.name;		
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public CardCollection getCards(){
		return new CardCollection(cards.values());		
	}
	
	public Card getCard(int i){
		return (Card)this.cards.get((Integer) i);	
	}
	
	public void addCard(int i, Card c){
		this.cards.put(i, c);		
	}
	
	public CardCollection getBoosterPack() {
		CardCollection bPack = new CardCollection();
		bPack.addCard(this.getCard(1));
		bPack.addCard(this.getCard(3));
		bPack.addCard(this.getCard(3));
		bPack.addCard(this.getCard(3));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		bPack.addCard(this.getCard(2));
		return bPack;
	}

}

package com.example.draft.Agent;

import com.example.draft.Card;
import com.example.draft.CardCollection;

public abstract class DraftAgent {
	
	public enum AgentType {Human, Computer};
	
	protected static String name;
	protected static CardCollection pool;
	protected static CardCollection deck;
	protected static CardCollection sideBoard;
	
	protected DraftAgent(){
		pool = new CardCollection();
		deck = new CardCollection();
		sideBoard = new CardCollection();
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String s){
		this.name = s;		
	}
	
	public CardCollection getPool(){
		return this.pool;
	}
	
	public void addCardToPool(Card c){
		this.pool.addCard(c);
	}

	public CardCollection getDeck(){
		return this.deck;
	}

	public void addCardToDeck(Card c){
		this.deck.addCard(c);
	}	

	public CardCollection sideBoard(){
		return this.sideBoard();
	}
	
	public void addCardToSideBoard(Card c){
		this.sideBoard.addCard(c);
	}
	
	public abstract AgentType getAgentType();
	
	public abstract void pickCard(CardCollection boosterPack);

}

package com.example.draft.Agent;

import java.util.Random;

import com.example.draft.Card;
import com.example.draft.CardCollection;

public class ComputerAgent extends DraftAgent {

	public ComputerAgent(){
		super();
	}
	
	@Override
	public AgentType getAgentType() {
		return AgentType.Computer;
	}

	@Override
	public void pickCard(CardCollection boosterPack) {
	    Random randomGenerator = new Random();
	    Card card =  boosterPack.getCard(randomGenerator.nextInt(boosterPack.getSize()));
	    boosterPack.removeCard(card);
	    this.addCardToPool(card);
	}
}

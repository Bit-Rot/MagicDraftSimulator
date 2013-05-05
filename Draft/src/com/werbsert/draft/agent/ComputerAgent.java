package com.werbsert.draft.agent;

import java.util.Random;

import com.werbsert.draft.model.Card;
import com.werbsert.draft.model.CardCollection;

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

package com.werbsert.draft.agent;

import java.util.Random;

import com.werbsert.draft.model.CardCollection;
import com.werbsert.draftcommon.model.Card;

public class ComputerAgent extends DraftAgent {

	public ComputerAgent(int numAgents){
		super(numAgents);
	}

	@Override
	public void pickCard(CardCollection boosterPack) {
	    Random randomGenerator = new Random();
	    Card card =  boosterPack.getCard(randomGenerator.nextInt(boosterPack.getSize()));
	    pickCardFromPack(card, boosterPack);
	}
}

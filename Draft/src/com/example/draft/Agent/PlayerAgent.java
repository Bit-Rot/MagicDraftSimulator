package com.example.draft.Agent;

import android.content.Intent;

import com.MagicDraft.CardDatabase.CardCollection;
import com.example.draft.BoosterViewActivity;
import com.example.draft.DraftEngine;

public class PlayerAgent extends DraftAgent{	
	
	public PlayerAgent(){
		super(); 
	}
	
	@Override
	public AgentType getAgentType() {
		return AgentType.Human; 
	}

	@Override
	public void pickCard(CardCollection boosterPack) {
		// launch booster pack pick		

/*	    
	    Intent boosterView = new Intent(this,BoosterViewActivity.class);
	    DraftEngine.this.startActivityForResult(boosterView, SET_SELECTION_MENU_ACTIVITY);
	   */ 
	}
	

}

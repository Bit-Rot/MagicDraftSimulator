package com.example.draft;


import com.MagicDraft.CardDatabase.CardCollection;
import com.MagicDraft.CardDatabase.CardDatabase;
import com.MagicDraft.CardDatabase.CardDatabase.Sets;
import com.example.draft.Agent.DraftAgent;
import com.example.draft.Agent.ComputerAgent;
import com.example.draft.Agent.PlayerAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class DraftEngine extends Activity {
	
	//activity codes
	private static final int SET_SELECTION_MENU_ACTIVITY = 0;
	private static final int BOOSTER_VIEW_ACTIVITY = 1;
	
	//static variables
	private static final int NUMBER_OF_AGENTS = 8;
	private enum ShiftDirection{PASS_LEFT, PASS_RIGHT};
	
	private CardDatabase.Sets pack1Set = null;
	private CardDatabase.Sets pack2Set = null;
	private CardDatabase.Sets pack3Set = null;
	private DraftAgent[] agents;	
	
	
	
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	//create agents
    	agents = new DraftAgent[NUMBER_OF_AGENTS];    	
    	runDraft();
	}
	
    private void runDraft() {
    	//Create agents
    	agents[0] = new PlayerAgent();
    	for (int i = 1; i < NUMBER_OF_AGENTS; i++){
    		agents[i] = new ComputerAgent();
    	}
    	
    	runDraftPicks(pack1Set, ShiftDirection.PASS_LEFT);
    	runDraftPicks(pack2Set, ShiftDirection.PASS_RIGHT);
    	runDraftPicks(pack3Set, ShiftDirection.PASS_LEFT);
    	
    	//run activity DeckBuilder
		
	}

	private void runDraftPicks(Sets set, ShiftDirection passDirection) {
		CardCollection[] boosterPacks = new CardCollection[NUMBER_OF_AGENTS];
		for(int i = 0; i < NUMBER_OF_AGENTS; i++){
			boosterPacks[i] = CardDatabase.getBoosterPack(set);
		}		
		int shift = 0;		
		while ( boosterPacks[0].getSize() > 0){			
			for(int agentNum = 0; agentNum < NUMBER_OF_AGENTS; agentNum++){
				agents[agentNum].pickCard(boosterPacks[(agentNum+shift)%NUMBER_OF_AGENTS]);
			}
			if(passDirection == ShiftDirection.PASS_LEFT){
				shift++;
			}
			else{
				shift--;
			}			
		}
		
	}

	private boolean getBoosterPackSets() {
    	Intent setSelectionMenu = new Intent(this,SetSelectionMenuActivity.class);
	    DraftEngine.this.startActivityForResult(setSelectionMenu, SET_SELECTION_MENU_ACTIVITY);
	    if (pack1Set == null){
	    	return false;
	    }
	    return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_SELECTION_MENU_ACTIVITY) {
            if (resultCode == RESULT_OK) {
            	pack1Set = (CardDatabase.Sets) data.getSerializableExtra("pack1Set");
            	pack2Set = (CardDatabase.Sets) data.getSerializableExtra("pack2Set");
            	pack3Set = (CardDatabase.Sets) data.getSerializableExtra("pack3Set");
            }
        }
    }

}

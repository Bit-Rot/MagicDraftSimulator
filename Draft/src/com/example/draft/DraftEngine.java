package com.example.draft;

import com.MagicDraft.CardDatabase.CardCollection;
import com.MagicDraft.CardDatabase.CardDatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class DraftEngine extends Activity {
	
	private static final int SET_SELECTION_MENU_ACTIVITY = 0;
	private static final int BOOSTER_VIEW_ACTIVITY = 0;
	private CardDatabase.Sets pack1Set = null;
	private CardDatabase.Sets pack2Set = null;
	private CardDatabase.Sets pack3Set = null;
	
	
	
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);    
    	runDraft();
	}
	
    private void runDraft() {
    	
    	//create agents
    	//ArrayList[Agent] agents = new ArrayList[Agent]();
    	
    	
        CardCollection boosterPack = CardDatabase.getBoosterPack(CardDatabase.Sets.RTR);
        if( !getBoosterPackSets()){
        	//error
        }
        
       
	    
	    Intent boosterView = new Intent(this,BoosterViewActivity.class);
	    DraftEngine.this.startActivityForResult(boosterView, SET_SELECTION_MENU_ACTIVITY); 
	    
	    
	    
		
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

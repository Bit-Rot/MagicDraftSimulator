package com.werbsert.draft.agent;

import android.app.Activity;
import android.content.Intent;

import com.werbsert.draft.activity.CardCollectionViewActivity;
import com.werbsert.draft.activity.CardCollectionViewActivity.CardCollectionParcelable;
import com.werbsert.draft.activity.DraftActivity.OnPickResultEvent;
import com.werbsert.draft.activity.DraftActivity.OnPickResultListener;
import com.werbsert.draft.model.CardCollection;
import com.werbsert.draftcommon.log.DebugLog;

public class PlayerAgent 
		extends DraftAgent 
		implements OnPickResultListener{

	protected Activity m_activity;
	
	public PlayerAgent(Activity activity, int numAgents){
		super(numAgents);
		m_activity = activity;
	}

	@Override
	public void pickCard(CardCollection boosterPack) {
	    
		//Create an intent to launch
	    Intent intent = new Intent(m_activity,CardCollectionViewActivity.class);
		
		//Jam the booster pack in there
		CardCollectionParcelable parcelable = new CardCollectionParcelable(boosterPack);
		intent.putExtra(CardCollectionViewActivity.CARD_COLLECTION_PARCEL_KEY, parcelable);
		
		//Rip it
		DebugLog.log("Launching CardCollectionView Activity for player");
	    m_activity.startActivityForResult(intent, CardCollectionViewActivity.RESULT_CARD_SELECTED);
	}

	public void onPickResult(OnPickResultEvent e) {
		pickCardFromPack(e.getSelectedCard(), e.getRemainingCards());
	}
}

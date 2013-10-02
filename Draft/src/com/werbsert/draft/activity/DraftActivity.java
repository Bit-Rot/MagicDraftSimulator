package com.werbsert.draft.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Window;

import com.werbsert.draft.BoosterPackGenerator;
import com.werbsert.draft.DraftPickScheduler;
import com.werbsert.draft.activity.CardCollectionViewActivity.CardSelectedParcelable;
import com.werbsert.draft.agent.ComputerAgent;
import com.werbsert.draft.agent.DraftAgent;
import com.werbsert.draft.model.CardCollection;
import com.werbsert.draft.model.DraftModel;
import com.werbsert.draft.service.CardService;
import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardSet;
import com.werbsert.draftcommon.model.SerializedCard;

public class DraftActivity extends Activity {
	
	public static final String SETS_SELECTED_PARCEL_KEY = "SelectedSets";
	
	//static variables
	private static final int NUMBER_OF_AGENTS = 8;
	
	private DraftPickScheduler m_scheduler;
	private DraftModel m_draftModel;
	protected List<OnPickResultListener> m_onPickResultListeners;
	
	/**
	 * Here we'll generate the booster packs and do some typical android-esque set up.  After that, we'll
	 * tell the scheduler to do its thing, which will trigger us to launch booster view activities for 
	 * selected cards, and handle the results accordingly.
	 * 
	 * TODO: If it ends up that booster pack generation is taking longer than, say, half a second or so,
	 * 		 we may want to look into a more graceful 'loading' presentation.
	 */
	@Override
	public void onCreate(Bundle b) {
		//Do android stuff
		super.onCreate(b);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
    	//Basic data initialization
		m_onPickResultListeners = new ArrayList<OnPickResultListener>();

    	//Initialize the model
    	m_draftModel = new DraftModel(NUMBER_OF_AGENTS);
//    	PlayerAgent playerAgent = new PlayerAgent(this, NUMBER_OF_AGENTS);
//    	playerAgent.setName("Player");
//    	m_draftModel.addAgent(playerAgent);
//    	m_onPickResultListeners.add(playerAgent);
    	for (int i = 0; i < NUMBER_OF_AGENTS; i++) {
    		ComputerAgent computerAgent = new ComputerAgent(NUMBER_OF_AGENTS);
    		computerAgent.setName("Computer #" + i);
    		m_draftModel.addAgent(computerAgent);
    	}
    	
    	//Second pass to associate each agent with its corresponding left/right agent
    	//Pretty sloppy, could be made nicer probably
    	for (int i = 0; i < NUMBER_OF_AGENTS; i++) {
    		DraftAgent currentAgent = m_draftModel.getAgent(i);
   			currentAgent.setLeftPlayer(m_draftModel.getAgent((i == 0)?NUMBER_OF_AGENTS-1 : i-1));
   			currentAgent.setRightPlayer(m_draftModel.getAgent((i == NUMBER_OF_AGENTS-1)?0:i+1));
    	}
    	
    	//Initialize the scheduler
    	m_scheduler = new DraftPickScheduler(m_draftModel);

    	//Get selected booster packs from the set selection activity
    	Bundle bundle = getIntent().getExtras();
    	BoosterSetParcelable boosterSetParcelable = (BoosterSetParcelable)bundle.getParcelable(SETS_SELECTED_PARCEL_KEY);
    	if (boosterSetParcelable == null) {
    		//TODO: Handle errors gracefully
    	}
    	
    	//For now we'll just create each agent with the same booster packs.  Use cases exist in which we may want to create different packs for each agent, however (grab bag).
    	for (DraftAgent agent : m_draftModel.getAgents()) {
	    	List<CardCollection> cardSetList = new ArrayList<CardCollection>();
	    	final CardCollection booster1 = BoosterPackGenerator.getBoosterPack(CardSet.getSet(boosterSetParcelable.getPack1()));
	    	final CardCollection booster2 = BoosterPackGenerator.getBoosterPack(CardSet.getSet(boosterSetParcelable.getPack1()));
	    	final CardCollection booster3 = BoosterPackGenerator.getBoosterPack(CardSet.getSet(boosterSetParcelable.getPack2()));
    		
	    	cardSetList.add(booster1);
	    	cardSetList.add(booster2);
	    	cardSetList.add(booster3);
    		agent.setBoosterPacks(cardSetList);
    	}
    	
    	//Fire it up, baby!
    	m_scheduler.runDraft();
	}
	
	/**
	 * Shit's a Parcelable for booster packs.  Nuff' said.
	 */
	public static class BoosterSetParcelable implements Parcelable {
        private String m_pack1;
        private String m_pack2;
        private String m_pack3;

        public String getPack1() {
			return m_pack1;
		}

		public String getPack2() {
			return m_pack2;
		}

		public String getPack3() {
			return m_pack3;
		}

		public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeString(m_pack1);
            out.writeString(m_pack2);
            out.writeString(m_pack3);
        }

        public static final Parcelable.Creator<BoosterSetParcelable> CREATOR = new Parcelable.Creator<BoosterSetParcelable>() {
            public BoosterSetParcelable createFromParcel(Parcel in) {
                return new BoosterSetParcelable(in);
            }

            public BoosterSetParcelable[] newArray(int size) {
                return new BoosterSetParcelable[size];
            }
        };
        
        private BoosterSetParcelable(Parcel in) {
            m_pack1 = in.readString();
            m_pack2 = in.readString();
            m_pack3 = in.readString();
        }
        
        public BoosterSetParcelable(String pack1, String pack2, String pack3) {
            m_pack1 = pack1;
            m_pack2 = pack2;
            m_pack3 = pack3;
        }
    }
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CardCollectionViewActivity.RESULT_CARD_SELECTED:
			onCardSelected(data);
		}
	}

	protected void onCardSelected(Intent data) {
		
		//TODO: This activity business is probably the wrong way to go about things - pull from data instead?
    	CardSelectedParcelable parcelable = (CardSelectedParcelable)data.getParcelableExtra(CardCollectionViewActivity.CARD_SELECTED_PARCEL_KEY);
		SerializedCard[] cards = parcelable.getCards();
		CardCollection boosterPack = new CardCollection();
		for (SerializedCard card : cards) {
			boosterPack.addCard(CardService.getInstance().getCard(CardSet.getSet(card.getSetCode()), card.getId()));
		}
		Card selectedCard = boosterPack.getCard(parcelable.getSelectionPosition());

		OnPickResultEvent e = new OnPickResultEvent(selectedCard, boosterPack);
    	for (OnPickResultListener listener : m_onPickResultListeners) {
    		listener.onPickResult(e);
    	}
	}
    
    public interface OnPickResultListener {
    	public void onPickResult(OnPickResultEvent e);
    }
    
    /**
     * Trigger this event whenever someone is done picking a card from their booster pack
     */
    public static class OnPickResultEvent {
    	Card m_selectedCard;
    	CardCollection m_remainingCards;
    	
    	public OnPickResultEvent(Card selectedCard, CardCollection remainingCards) {
    		m_selectedCard = selectedCard;
    		m_remainingCards = remainingCards;
    	}

		public Card getSelectedCard() {
			return m_selectedCard;
		}

		public CardCollection getRemainingCards() {
			return m_remainingCards;
		}
    }

}

package com.werbsert.draft.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.app.Activity;

import com.werbsert.draft.model.CardCollection;
import com.werbsert.draftcommon.log.DebugLog;
import com.werbsert.draftcommon.model.Card;

public abstract class DraftAgent {
	
	protected String m_name;
	protected CardCollection m_pool;
	protected List<CardCollection> m_boosterPacks;
	protected DraftAgent m_leftPlayer;
	protected DraftAgent m_rightPlayer;
	protected BlockingQueue<CardCollection> m_boosterQueue;
	protected boolean m_picking;
	protected Activity m_activity;
	
	protected List<OnPickCompleteListener> m_onPickCompleteListeners;
	protected List<OnBoosterPackCompleteListener> m_onBoosterPackCompleteListeners;

	public abstract void pickCard(CardCollection boosterPack);
	
	protected DraftAgent(int numAgents) {
		m_pool = new CardCollection();
		m_onPickCompleteListeners = new ArrayList<OnPickCompleteListener>();
		m_onBoosterPackCompleteListeners = new ArrayList<OnBoosterPackCompleteListener>();
    	m_boosterQueue = new ArrayBlockingQueue<CardCollection>(1);
    	m_picking = false;
	}
	
	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		m_name = name;
	}

	public List<CardCollection> getBoosterPacks() {
		return m_boosterPacks;
	}

	public void setBoosterPacks(List<CardCollection> boosterPacks) {
		m_boosterPacks = boosterPacks;
	}
	
	public CardCollection getPool() {
		return m_pool;
	}

	public void setPool(CardCollection pool) {
		m_pool = pool;
	}
	
	public DraftAgent getLeftPlayer() {
		return m_leftPlayer;
	}
	
	public void setLeftPlayer(DraftAgent leftPlayer) {
		m_leftPlayer = leftPlayer;
	}
	
	public DraftAgent getRightPlayer() {
		return m_rightPlayer;
	}
	
	public void setRightPlayer(DraftAgent rightPlayer) {
		m_rightPlayer = rightPlayer;
	}

	public boolean isPicking() {
		return m_picking;
	}
	
	public void setPicking(boolean picking) {
		m_picking = picking;
	}
	
	public Activity getActivity() {
		return m_activity;
	}
	
	public void setActivity(Activity activity) {
		m_activity = activity;
	}
	
	public void pickCardFromPack(Card card, CardCollection boosterPack) {
		m_pool.addCard(boosterPack.removeCard(card));
		
		//important to check if booster is empty before shipping pickComplete event -
		//the booster is a shared resource, and if a computer picks between the event
		//and the check (very likely!), race conditions are prone to occur.
		boolean isBoosterEmpty = boosterPack.getSize() <= 0;
		
		DebugLog.log(m_name + " picked " + card.getName() + " from pack.  Cards remaining: " + boosterPack.getSize());
		onPickComplete(new OnPickCompleteEvent(this, boosterPack));
		
		if (isBoosterEmpty) {
			DebugLog.log(m_name + ": Booster Pack Complete");
			onBoosterPackComplete(new OnBoosterPackCompleteEvent(this));
		}
	}

	public void addCardToPool(Card c) {
		m_pool.addCard(c);
	}
	
	public void addOnPickCompleteListener(OnPickCompleteListener listener) {
		m_onPickCompleteListeners.add(listener);
	}
	
	public void removeOnPickCompleteListener(OnPickCompleteListener listener) {
		m_onPickCompleteListeners.remove(listener);
	}
	
	public void addOnBoosterPackCompleteListener(OnBoosterPackCompleteListener listener) {
		m_onBoosterPackCompleteListeners.add(listener);
	}
	
	public void removeOnBoosterPackCompleteListener(OnBoosterPackCompleteListener listener) {
		m_onBoosterPackCompleteListeners.remove(listener);
	}
	
	public void pushCardsToQueue(CardCollection cards) {
		try {
			m_boosterQueue.put(cards);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Open the next available booster pack.
	 */
	public void openNextPack() {
		//Pop queue off pack list, push to queue
		pushCardsToQueue(m_boosterPacks.remove(0));
		
		while (m_picking) {
			CardCollection currentBooster;
			try {
				currentBooster = m_boosterQueue.take();
				pickCard(currentBooster);
			} catch (InterruptedException e) {
				//TODO: Handle this exception gracefully
			}
		}
	}

	/**
	 * Notify listeners that a card has been picked
	 */
    protected void onPickComplete(OnPickCompleteEvent e) {
    	for (OnPickCompleteListener listener : m_onPickCompleteListeners) {
    		listener.onPickComplete(e);
    	}
    }
    
    protected void onBoosterPackComplete(OnBoosterPackCompleteEvent e) {
    	for (OnBoosterPackCompleteListener listener : m_onBoosterPackCompleteListeners) {
    		listener.onBoosterPackComplete(e);
    	}
    }
    
    public interface OnPickCompleteListener {
    	public void onPickComplete(OnPickCompleteEvent e);
    }
    
    /**
     * Trigger this event whenever someone is done picking a card from their booster pack
     */
    public static class OnPickCompleteEvent {
    	DraftAgent m_draftAgent;
    	CardCollection m_remainingCards;
    	
    	public OnPickCompleteEvent(DraftAgent draftAgent, CardCollection remainingCards) {
    		m_draftAgent = draftAgent;
    		m_remainingCards = remainingCards;
    	}

		public DraftAgent getDraftAgent() {
			return m_draftAgent;
		}

		public CardCollection getRemainingCards() {
			return m_remainingCards;
		}
    }
    
    public interface OnBoosterPackCompleteListener {
    	public void onBoosterPackComplete(OnBoosterPackCompleteEvent e);
    }
    
    /**
     * Trigger this event when all the cards from all boosters in a cycle (eg., booster #1, #2, or #3)
     * have been picked by agents.
     */
    public static class OnBoosterPackCompleteEvent {
    	private DraftAgent m_draftAgent;
    	
    	public OnBoosterPackCompleteEvent(DraftAgent draftAgent) {
    		m_draftAgent = draftAgent;
    	}
    	
    	public DraftAgent getDraftAgent() {
    		return m_draftAgent;
    	}
    }
}

package com.werbsert.draft;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.werbsert.draft.agent.DraftAgent;
import com.werbsert.draft.agent.DraftAgent.OnBoosterPackCompleteEvent;
import com.werbsert.draft.agent.DraftAgent.OnBoosterPackCompleteListener;
import com.werbsert.draft.agent.DraftAgent.OnPickCompleteEvent;
import com.werbsert.draft.agent.DraftAgent.OnPickCompleteListener;
import com.werbsert.draft.model.DraftModel;
import com.werbsert.draftcommon.log.DebugLog;

public class DraftPickScheduler {
	
	//Janky static variable
	private static int NUM_ROUNDS = 3;
	
	private DraftModel m_draftModel;
	private List<OnDraftCompleteListener> m_onDraftCompleteListeners;
	private Semaphore m_boosterPackSemaphore;
	private boolean m_passingLeft;
	
	public DraftPickScheduler(DraftModel draftModel) {
		m_draftModel = draftModel;
		m_onDraftCompleteListeners = new ArrayList<OnDraftCompleteListener>();
		m_boosterPackSemaphore = new Semaphore(1);
		m_passingLeft = true;
	}
	
    public void runDraft() {
	    new Thread(new Runnable() {
	        public void run() {
	    
		    	//Initialize agents with listeners for various agent events
		    	for (DraftAgent agent : m_draftModel.getAgents()) {
		    		agent.addOnPickCompleteListener(new OnPickCompleteListener() {
						public void onPickComplete(OnPickCompleteEvent e) {
							handlePickComplete(e);
						}
		    		});
		    		agent.addOnBoosterPackCompleteListener(new OnBoosterPackCompleteListener() {
						public void onBoosterPackComplete(OnBoosterPackCompleteEvent e) {
							handleBoosterPackComplete(e);
						}
		    		});
		    	}
		    	
		    	//Do some stupid gross stuff to get each agent to pick
		    	for (int packNumber = 0; packNumber < NUM_ROUNDS; packNumber++) {
		    		try {
		    			DebugLog.log("Scheduler: Drafting Pack #" + (packNumber + 1) + ". Acquiring Draft Round Lock...");
		    			m_boosterPackSemaphore.acquire(); 
		    			DebugLog.log("Scheduler: Draft Round Lock Acquired");
				    	for (DraftAgent agent : m_draftModel.getAgents()) {
				    		agent.setPicking(true);
				    	}
				    	for (DraftAgent agent : m_draftModel.getAgents()) {
				    		final DraftAgent finalAgent = agent;
				    	    new Thread(new Runnable() {
				    	        public void run() {
				    	        	DebugLog.log("Scheduler: Spinning openNextPack thread for " + finalAgent.getName());
						    		finalAgent.openNextPack();
				    	        }
				    	    }).start();
				    	}
		    		} catch (Exception e) {
		    			//TODO: Something went terribly, terribly wrong.  Error out.
		    		}
		    	}
		    	
		    	//Signal that the draft is complete
		    	try {
		    		m_boosterPackSemaphore.acquire(); 
		    	} catch (Exception e) {
	    			//TODO: Something went terribly, terribly wrong.  Error out.
		    	}
		    	onDraftComplete(new OnDraftCompleteEvent());
    			m_boosterPackSemaphore.release(); 
	        }
	    }).start();
	}
    
    /**
     * Handle the completion of a pick for a given agent
     */
    private void handlePickComplete(OnPickCompleteEvent e) {
    	DebugLog.log("Scheduler: Handling Pick Complete Event from " + e.getDraftAgent().getName() + ". " + e.getRemainingCards().getSize() + " cards remaining");
    	DraftAgent agent = e.getDraftAgent();
    	DraftAgent nextAgent = m_passingLeft? agent.getLeftPlayer() : agent.getRightPlayer();
    	if (e.getRemainingCards() != null && e.getRemainingCards().getSize() > 0) {
    		nextAgent.pushCardsToQueue(e.getRemainingCards());
    	}
    }
    
    /**
     * Handle the completion of a booster pack for a given agent
     * 
     * TODO: This logic (starting from DraftAgent::pickCardFromPack()) is not correctly synchronized.
     */
    synchronized private void handleBoosterPackComplete(OnBoosterPackCompleteEvent e) {
    	//We set picking = false in this synchronized method instead of having the agent itself manage its status
    	// because it led to a race condition in which multiple agents would mark themselves as not picking, trigger 
    	// boosterPackComplete events, and the scheduler would continue with the next pack before some agents were
    	// done executing the event.
    	e.getDraftAgent().setPicking(false);
    	
		boolean m_roundComplete = true;
		DebugLog.log("Scheduler: Handling Booster Complete Event from " + e.getDraftAgent().getName());
		for (DraftAgent draftAgent : m_draftModel.getAgents()) {
			if (draftAgent.isPicking()) {
				m_roundComplete = false;
			}
		}
		
		if (m_roundComplete) {
			DebugLog.log("Scheduler: Round Complete!  Releasing Booster Round Lock...");
			m_boosterPackSemaphore.release();
			DebugLog.log("Scheduler: Round Complete!  Released Booster Round Lock");
		}
    }
    
    /**
     * Notify listeners that the draft is complete.
     * @param e
     */
    private void onDraftComplete(OnDraftCompleteEvent e) {
    	for (OnDraftCompleteListener listener : m_onDraftCompleteListeners) {
    		listener.onDraftComplete(e);
    	}
    }
    
    public interface OnDraftCompleteListener {
    	public void onDraftComplete(OnDraftCompleteEvent e);
    }
    
    /**
     * Trigger this event when the last card from the last booster pack has been selected.
     */
    public static class OnDraftCompleteEvent {}
}

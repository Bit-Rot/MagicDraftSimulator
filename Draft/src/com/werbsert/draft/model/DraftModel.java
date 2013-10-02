package com.werbsert.draft.model;

import java.util.ArrayList;
import java.util.List;

import com.werbsert.draft.agent.DraftAgent;

public class DraftModel {
	private int m_draftSize;
	private List<DraftAgent> m_draftAgents;
	
	public DraftModel(int draftSize) {
		m_draftSize = draftSize;
		m_draftAgents = new ArrayList<DraftAgent>(draftSize);
	}
	
	public void addAgent(DraftAgent agent) {
		if (m_draftAgents.size() < m_draftSize) {
			m_draftAgents.add(agent);
		}
	}
	
	public List<DraftAgent> getAgents() {
		return m_draftAgents;
	}
	
	public DraftAgent getAgent(int position) {
		return m_draftAgents.get(position);
	}
}

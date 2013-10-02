package com.werbsert.draftcommon.model;

import java.io.Serializable;

/**
 * A serialized version of a card; containing the most minimal amount of information
 * necessary to reliably store/retrieve the state of a card.
 */
public class SerializedCard
	implements Serializable {
	
	private static final long serialVersionUID = -4247418152037745290L;
	
	private long m_id;
	private String m_setCode;
	
	public SerializedCard (Card card) {
		m_id = card.getId();
		m_setCode = card.getSet().getCode();
	}

	public long getId() {
		return m_id;
	}

	public void setId(long id) {
		m_id = id;
	}

	public String getSetCode() {
		return m_setCode;
	}

	public void setSetCode(String setCode) {
		m_setCode = setCode;
	}
}

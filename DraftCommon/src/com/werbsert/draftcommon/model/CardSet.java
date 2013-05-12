package com.werbsert.draftcommon.model;

import java.util.HashMap;


/**
 * A MTG Expansion.  Might want to store this information in the database
 */
public enum CardSet {
//	INN ("INN", "Innistrad"),
//	DKA ("DKA", "Dark Ascension"),
//	AVR ("AVR", "Avacyn Restored"),
	M13 ("M13", "Magic 2013"), //, 114, 64, 58, 15, 5),
	RTR ("RTR", "Return to Ravnica");//, 108, 82, 53, 15, 5);

	private String m_shortName;
	private String m_longName;
	
	private static HashMap<String, CardSet> s_setLookupMap = new HashMap<String, CardSet>();
	
	static {
//		s_setLookupMap.put(INN.m_shortName, INN);
//		s_setLookupMap.put(DKA.m_shortName, DKA);
//		s_setLookupMap.put(AVR.m_shortName, AVR);
		s_setLookupMap.put(M13.m_shortName, M13);
		s_setLookupMap.put(RTR.m_shortName, RTR);
	}
	
	public static CardSet getSet(String shortName) {
		return s_setLookupMap.get(shortName);
	}
	
	private CardSet(String shortName, 
			String longName) {
		m_shortName = shortName;
		m_longName = longName;
	}
	
	public String getShortName() {
		return m_shortName;
	}

	public String getLongName() {
		return m_longName;
	}
}

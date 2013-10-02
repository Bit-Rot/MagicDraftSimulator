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

	private String m_code;
	private String m_longName;
	
	private static HashMap<String, CardSet> s_setLookupMap = new HashMap<String, CardSet>();
	
	static {
//		s_setLookupMap.put(INN.m_shortName, INN);
//		s_setLookupMap.put(DKA.m_shortName, DKA);
//		s_setLookupMap.put(AVR.m_shortName, AVR);
		s_setLookupMap.put(M13.m_code, M13);
		s_setLookupMap.put(RTR.m_code, RTR);
	}
	
	public static CardSet getSet(String code) {
		return s_setLookupMap.get(code);
	}
	
	private CardSet(String code, 
			String longName) {
		m_code = code;
		m_longName = longName;
	}
	
	public String getCode() {
		return m_code;
	}

	public String getLongName() {
		return m_longName;
	}
}

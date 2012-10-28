package com.example.draft;

import java.util.HashMap;

public enum CardRarity {
	LAND ("LAND", "N/A"),
	COMMON ("COMMON", "Common"),
	UNCOMMON ("UNCOMMON", "Uncommon"),
	RARE ("RARE", "Rare"),
	MYTHIC ("MYTHIC", "Mythic Rare"),
	SPECIAL ("SPECIAL", "Special"), //From gatherer, dunno what this is though.  Probably a legacy thing.
	PROMO ("PROMO", "Special");
	
	private String m_id;
	private String m_name;
	
	private static HashMap<String, CardRarity> s_rarityLookupMap = new HashMap<String, CardRarity>();
	
	static {
		s_rarityLookupMap.put(LAND.m_id, LAND);
		s_rarityLookupMap.put(COMMON.m_id, COMMON);
		s_rarityLookupMap.put(UNCOMMON.m_id, UNCOMMON);
		s_rarityLookupMap.put(RARE.m_id, RARE);
		s_rarityLookupMap.put(MYTHIC.m_id, MYTHIC);
	}
	
	public static CardRarity getRarity(String shortName) {
		return s_rarityLookupMap.get(shortName);
	}
	
	private CardRarity(String id, String name) {
		m_id = id;
		m_name = name;
	}
	
	public String getId() {
		return m_id;
	}

	public String getName() {
		return m_name;
	}
}

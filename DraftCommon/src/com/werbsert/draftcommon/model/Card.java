package com.werbsert.draftcommon.model;

public class Card {
	
	private long m_id;
	private String m_name;
	private String m_mana;
	private Integer m_cmc;
	private String m_type;
	private String m_text;
	private String m_flavor;
	private Integer m_power;
	private Integer m_toughness;
	private CardSet m_set;
	private String m_imageUrl;
	private CardRarity m_rarity;
	private Integer m_number;
	private String m_artist;
	private Integer m_multiverseId;
	
	public Card (long id, 
			String name, 
			String mana, 
			Integer cmc, 
			String type, 
			String text, 
			String flavor, 
			Integer power, 
			Integer toughness, 
			CardSet set, 
			CardRarity rarity, 
			Integer number, 
			String artist, 
			Integer multiverseId,
			String imageUrl) {
		m_id = id;
		m_name = name;
		m_mana = mana;
		m_cmc = cmc;
		m_text = text;
		m_power = power;
		m_toughness = toughness;
		m_set = set;
		m_rarity = rarity;
		m_number = number;
		m_artist = artist;
		m_multiverseId = multiverseId;
		m_imageUrl = imageUrl;
	}
	
	public long getId() {
		return m_id;
	}

	//public accessors
	public int getNumber(){
		return this.m_number;
	}
	
	public String getName(){
		return this.m_name;
	}
	
	public String getMana(){
		return this.m_mana;
	}
	
	public String getImageUrl(){
		return this.m_imageUrl;
	}
	
	public CardRarity getRarity(){
		return this.m_rarity;
	}

	public int getCmc() {
		return m_cmc;
	}

	public String getText() {
		return m_text;
	}

	public Integer getPower() {
		return m_power;
	}

	public Integer getToughness() {
		return m_toughness;
	}

	public CardSet getSet() {
		return m_set;
	}

	public String getArtist() {
		return m_artist;
	}

	public int getMultiverseId() {
		return m_multiverseId;
	}

	public String getType() {
		return m_type;
	}

	public String getFlavor() {
		return m_flavor;
	}
}

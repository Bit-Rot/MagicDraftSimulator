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
	
	public Card () {
		
	}
	
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

	public void setId(long id) {
		m_id = id;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		m_name = name;
	}

	public String getMana() {
		return m_mana;
	}

	public void setMana(String mana) {
		m_mana = mana;
	}

	public Integer getCmc() {
		return m_cmc;
	}

	public void setCmc(Integer cmc) {
		m_cmc = cmc;
	}

	public String getType() {
		return m_type;
	}

	public void setType(String type) {
		m_type = type;
	}

	public String getText() {
		return m_text;
	}

	public void setText(String text) {
		m_text = text;
	}

	public String getFlavor() {
		return m_flavor;
	}

	public void setFlavor(String flavor) {
		m_flavor = flavor;
	}

	public Integer getPower() {
		return m_power;
	}

	public void setPower(Integer power) {
		m_power = power;
	}

	public Integer getToughness() {
		return m_toughness;
	}

	public void setToughness(Integer toughness) {
		m_toughness = toughness;
	}

	public CardSet getSet() {
		return m_set;
	}

	public void setSet(CardSet set) {
		m_set = set;
	}

	public String getImageUrl() {
		return m_imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		m_imageUrl = imageUrl;
	}

	public CardRarity getRarity() {
		return m_rarity;
	}

	public void setRarity(CardRarity rarity) {
		m_rarity = rarity;
	}

	public Integer getNumber() {
		return m_number;
	}

	public void setNumber(Integer number) {
		m_number = number;
	}

	public String getArtist() {
		return m_artist;
	}

	public void setArtist(String artist) {
		m_artist = artist;
	}

	public Integer getMultiverseId() {
		return m_multiverseId;
	}

	public void setMultiverseId(Integer multiverseId) {
		m_multiverseId = multiverseId;
	}
}

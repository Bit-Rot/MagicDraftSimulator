package com.example.draft;

public class Card {
	
	public enum Rarity {COMMON, UNCOMMON, RARE, MYTHIC_RARE};
	
	private int number;
	private String name;
	private boolean isFoil;
	private String cost;
	private String image;	
	private Rarity rarity;
	private int imageId;
	private int convertedManaCost;
	
	
	/*
	// Pricing
	private double low;
	private double mid;
	private double high;
	*/
	
	public Card (){		
	}
	
	//public accessors
	public int getNumber(){
		return this.number;
	}
	
	public void setNumber(int i){
		this.number = i;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String s){
		this.name = s;  
	}
	
	public String getcost(){
		return this.cost;
	}
	
	public void setCost(String s){
		this.cost = s;  
	}
	
	public String getImage(){
		return this.image;
	}
	
	public void setImage(String s){
		this.image = s;  
	}
	
	public Rarity getRarity(){
		return this.rarity;
	}
	
	public void setRarity(Rarity r){
		this.rarity = r;		
	}

	public int getImageId() {
		return this.imageId;
	}
	
	public void setImageId(int i){
		this.imageId = i;
	}
	
	public boolean isFoil(){
		return this.isFoil;
	}
	
	public void setIsFoil(boolean b){
		this.isFoil = b;
	}

	public int getCMC(){
		return convertedManaCost;
	}
	
	public void setCMC(int i){
		this.convertedManaCost = i;
	}
	
	//More complicated functions
	
	
	

}

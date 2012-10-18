package com.example.draft;

public class Card {
	
	public enum Rarity {COMMON, UNCOMMON, RARE, MYTHIC_RARE};
	
	private int number;
	private String name;
	private String cost;
	private String image;	
	private Rarity rarity;
	private int imageId;
	
	
	/*
	// Pricing
	private double low;
	private double mid;
	private double high;
	*/
	
	public Card (){		
	}
	
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
	
	public void setRarioty(Rarity r){
		this.rarity = r;		
	}

	public int getImageId() {
		return this.imageId;
	}
	
	public void setImageId(int i){
		this.imageId = i;
	}

}
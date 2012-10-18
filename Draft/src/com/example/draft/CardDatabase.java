package com.example.draft;

import java.util.HashMap;


public class CardDatabase {
	
	//sets
	public static enum Sets {RTR, M13, AVR, DKA, INN};
	
	//singleton class
	private static CardDatabase database = null;
	private HashMap<Sets, CardSet> cardSets;	
	
	//private constructor
	private CardDatabase(){
		cardSets = new HashMap<Sets, CardSet>();
		
		
		//load stuff
		//temp stuff		
		CardSet rtr = new CardSet();
		
		Card card1 = new Card();
		card1.setCost("4WWW");
		card1.setImage("RTR_1.jpg");		
		card1.setName("Angel of Serenity");
		card1.setNumber(1);
		card1.setImageId(R.drawable.rtr_1);
		card1.setRarioty(Card.Rarity.MYTHIC_RARE);
		rtr.addCard(1, card1);

		Card card2 = new Card();
		card2.setCost("3W");
		card2.setImage("RTR_2.jpg");
		card2.setName("Armory Guard");
		card2.setNumber(2);
		card2.setImageId(R.drawable.rtr_2);
		card2.setRarioty(Card.Rarity.COMMON);
		rtr.addCard(2, card2);
		
		Card card3 = new Card();
		card3.setCost("2W");
		card3.setImage("RTR_3.jpg");
		card3.setName("Arrest");
		card3.setNumber(3);
		card3.setImageId(R.drawable.rtr_3);
		card3.setRarioty(Card.Rarity.UNCOMMON);
		rtr.addCard(3, card3);
		
		cardSets.put(Sets.RTR, rtr);
		
	}
	
	//public accessor
	private static CardDatabase getCardDatabase(){
		if (database == null){
			database = new CardDatabase();
		}
		return database;
	}
	
	public CardSet getCardSet(Sets set){
		return (CardSet)cardSets.get(set);
	}
	
	public Card getCard(Sets set, int cNumber){
		return ((CardSet)cardSets.get(set)).getCard(cNumber);
	}

	public static CardCollection getBoosterPack(Sets set) {
		return getCardDatabase().getCardSet(Sets.RTR).getBoosterPack();
		
	}

}

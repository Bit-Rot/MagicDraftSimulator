package com.MagicDraft.CardDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.draft.R;
import com.example.draft.R.drawable;


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
		
		rtr.setName("Return to Ravnica");
		
		Card card1 = new Card();
		card1.setCost("4WWW");
		card1.setImage("RTR_1.jpg");		
		card1.setName("Angel of Serenity");
		card1.setNumber(1);
		card1.setImageId(R.drawable.rtr_1);
		card1.setRarity(Card.Rarity.MYTHIC_RARE);
		rtr.addCard(1, card1);

		Card card2 = new Card();
		card2.setCost("3W");
		card2.setImage("RTR_2.jpg");
		card2.setName("Armory Guard");
		card2.setNumber(2);
		card2.setImageId(R.drawable.rtr_2);
		card2.setRarity(Card.Rarity.COMMON);
		rtr.addCard(2, card2);
		
		Card card3 = new Card();
		card3.setCost("2W");
		card3.setImage("RTR_3.jpg");
		card3.setName("Arrest");
		card3.setNumber(3);
		card3.setImageId(R.drawable.rtr_3);
		card3.setRarity(Card.Rarity.UNCOMMON);
		rtr.addCard(3, card3);
		
		

		CardSet m13 = new CardSet();
		m13.setName("Magic 2013");

		CardSet avr = new CardSet();
		avr.setName("Avacyn Restored");

		CardSet dka = new CardSet();
		dka.setName("Dark Ascension");

		CardSet inn = new CardSet();
		inn.setName("Innistrad");
		
		cardSets.put(Sets.RTR, rtr);
		cardSets.put(Sets.M13, m13);
		cardSets.put(Sets.AVR, avr);
		cardSets.put(Sets.DKA, dka);
		cardSets.put(Sets.INN, inn);
		
		
	}
	
	//public accessor
	private static CardDatabase getCardDatabase(){
		if (database == null){
			database = new CardDatabase();
		}
		return database;
	}
	
	public CardSet getCardSet(Sets set){
		getCardDatabase();
		return (CardSet)cardSets.get(set);
	}
	
	public Card getCard(Sets set, int cNumber){
		getCardDatabase();
		return ((CardSet)cardSets.get(set)).getCard(cNumber);
	}

	public static CardCollection getBoosterPack(Sets set) {		
		return getCardDatabase().getCardSet(Sets.RTR).getBoosterPack();
		
	}

	public static List<String> getAvailableSets() {
		List<String> list = new ArrayList<String>();
		CardDatabase cdb = getCardDatabase();
		for (Sets s: Sets.values()){
			CardSet cardSet = cdb.getCardSet(s);
			if(cardSet != null){
				list.add(cardSet.getName());
			}			
		}
		return list;
	}

}

package com.werbsert.draft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.werbsert.draft.model.CardCollection;
import com.werbsert.draft.service.CardService;
import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardRarity;
import com.werbsert.draftcommon.model.CardSet;

public class BoosterPackGenerator {
	private static HashMap<CardSet, AbstractPackGenerator> s_generatorMap;
	
	public static CardCollection getBoosterPack(CardSet set) {
		//Pull the appropriate generator
		AbstractPackGenerator generator = s_generatorMap.get(set);
		
		//Build the collection
		CardCollection boosterPack = null;
		if (generator != null) {
			boosterPack = generator.generatePack();
		}
		return boosterPack;
	}
	
	static {
		//Initialize set -> generator mapping
		s_generatorMap = new HashMap<CardSet, AbstractPackGenerator>();
//		s_generatorMap.put(CardSet.INN, new FlipCardPackGenerator(CardSet.INN));
//		s_generatorMap.put(CardSet.DKA, new FlipCardPackGenerator(CardSet.DKA));
//		s_generatorMap.put(CardSet.AVR, new FlipCardPackGenerator(CardSet.AVR));
		s_generatorMap.put(CardSet.M13, new GenericPackGenerator(CardSet.M13));
		s_generatorMap.put(CardSet.RTR, new GenericPackGenerator(CardSet.RTR));
	}
	
	/**
	 * Abstract class for generating packs with specific formats.  The contents of booster
	 * packs have changed over the years, this class helps accommodate any set-specific 
	 * oddities.
	 * 
	 * @author Andrew
	 */
	private static abstract class AbstractPackGenerator {
		protected CardSet m_set;
		
		public AbstractPackGenerator(CardSet set) {
			m_set = set;
		}
		/**
		 * Generates a booster pack.  See javadocs for subclasses for more information.
		 * @return
		 */
		public abstract CardCollection generatePack();
	}
	
	/**
	 * Generic pack generator.  Generates:
	 * 
	 * 1 Rare (w/ chance of mythic)
	 * 3 Uncommons
	 * 10 Commons (w/ chance of foil)
	 * 1 Basic Land
	 */
	private static class GenericPackGenerator extends AbstractPackGenerator {
		private static final double CHANCE_OF_MYTHIC_RARE = 0.15D;
		private static final double CHANCE_OF_FOIL = 0.1D;
		private static final double CHANCE_OF_FOIL_LAND = (10D/150D);
		private static final double CHANCE_OF_FOIL_COMMON = (100D/150D);
		private static final double CHANCE_OF_FOIL_UNCOMMON = (30D/150D);
		private static final double CHANCE_OF_FOIL_RARE = (8D/150D);
		private static final double CHANCE_OF_FOIL_MYTHIC_RARE = (2D/150D);
		
		public GenericPackGenerator(CardSet set) {
			super(set);
		}

		public CardCollection generatePack() {

			List<Card> cardList = CardService.getInstance().getCardsBySet(m_set);
	        CardCollection entireSet = new CardCollection(cardList);
	        
	        Random random = new Random();
			
			//Decide on rare or mythic rare
			boolean isRareMythic = random.nextDouble() < CHANCE_OF_MYTHIC_RARE;
			
			//Decide on Foil or no foil
			//TODO: This could be done more elegantly.  Feel free to refactor.  Any ideas?
			CardRarity foilRarity = null;
			boolean isFoil = random.nextDouble() < CHANCE_OF_FOIL;
			if (isFoil) {
				double foilRoll = random.nextDouble();
				double accumulatedPercentChance = CHANCE_OF_FOIL_LAND;
				if (foilRoll < accumulatedPercentChance) {
					foilRarity = CardRarity.LAND;
				} else if (foilRoll < (accumulatedPercentChance += CHANCE_OF_FOIL_COMMON)) {
					foilRarity = CardRarity.COMMON;
				} else if (foilRoll < (accumulatedPercentChance += CHANCE_OF_FOIL_UNCOMMON)) {
					foilRarity = CardRarity.UNCOMMON;
				} else if (foilRoll < (accumulatedPercentChance += CHANCE_OF_FOIL_RARE)) {
					foilRarity = CardRarity.RARE;
				} else if (foilRoll < (accumulatedPercentChance += CHANCE_OF_FOIL_MYTHIC_RARE)) {
					foilRarity = CardRarity.MYTHIC;
				}
			}
			
			//Generate rare/mythic
			CardCollection boosterPack = new CardCollection();
			if (isRareMythic) {
				List<Card> rareCards = entireSet.getCardsByRarity(CardRarity.MYTHIC);
				boosterPack.addCard(rareCards.get(random.nextInt(rareCards.size())));
			} else {
				List<Card> rareCards = entireSet.getCardsByRarity(CardRarity.RARE);
				boosterPack.addCard(rareCards.get(random.nextInt(rareCards.size())));
			}
			
			//Randomly pick 3 uncommons
			Set<Card> uncommonsPool = new HashSet<Card>();
			uncommonsPool.addAll(entireSet.getCardsByRarity(CardRarity.UNCOMMON));
			for (int i=0; i<3; ++i) {
				int index = random.nextInt(uncommonsPool.size());
				boosterPack.addCard((Card)uncommonsPool.toArray()[index]);
				uncommonsPool.remove(uncommonsPool.toArray()[index]);
			}
			
			//Randomly pick 10 commons (or 9 and a foil)
			int numCommons = (isFoil)? 9 : 10;
			Set<Card> commonsPool = new HashSet<Card>();
			commonsPool.addAll(entireSet.getCardsByRarity(CardRarity.COMMON));
			for (int i=0; i<numCommons; ++i) {
				int index = random.nextInt(commonsPool.size());
				boosterPack.addCard((Card)commonsPool.toArray()[index]);
				commonsPool.remove(commonsPool.toArray()[index]);
			}
			
			//Generate foil
			if (isFoil) {
				List<Card> possibleFoils = entireSet.getCardsByRarity(foilRarity);
				//We technically shouldn't need to make this check, but it occurs with test data.
				if (!possibleFoils.isEmpty()) {
					boosterPack.addCard(possibleFoils.get(random.nextInt(possibleFoils.size())));
				}
			}
			return boosterPack;
		}
	}
	
	/**
	 * Pack generator for sets with flipcards.
	 *
	private static class FlipCardPackGenerator extends AbstractPackGenerator {

		public FlipCardPackGenerator(CardSet set) {
			super(set);
		}

		@Override
		public CardCollection generatePack() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	*/
}

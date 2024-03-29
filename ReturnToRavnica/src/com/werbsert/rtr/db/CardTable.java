package com.werbsert.rtr.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.werbsert.draftcommon.db.DraftContract;
import com.werbsert.draftcommon.model.Card;
import com.werbsert.draftcommon.model.CardRarity;
import com.werbsert.draftcommon.model.CardSet;

/**
 * TODO: Expand upon this class, I think this is supposed to encompass other objects, such as draft histories, etc
 * CardDatabase provides direct access to a SQL database.
 */
public class CardTable {

	public static final String DATABASE_NAME = "com.werbsert.rtr.card";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_CARD = "t_card";
	
	private static final String DATABASE_CREATE = "create table " + TABLE_CARD + "(" +
		DraftContract.Card.COLUMN_ID + " integer primary key autoincrement, " +
		DraftContract.Card.COLUMN_NAME + " text not null, " +
		DraftContract.Card.COLUMN_MANA + " text, " +
		DraftContract.Card.COLUMN_CMC + " text, " +
		DraftContract.Card.COLUMN_TYPE + " text not null, " +
		DraftContract.Card.COLUMN_TEXT + " text, " +
		DraftContract.Card.COLUMN_FLAVOR + " text, " +
		DraftContract.Card.COLUMN_POWER + " text, " +
		DraftContract.Card.COLUMN_TOUGHNESS + " text, " +
		DraftContract.Card.COLUMN_SET + " text not null, " +
		DraftContract.Card.COLUMN_RARITY + " text not null, " +
		DraftContract.Card.COLUMN_NUMBER + " text not null, " +
		DraftContract.Card.COLUMN_ARTIST + " text not null, " +
		DraftContract.Card.COLUMN_MULTIVERSE_ID + " text not null, " +
		DraftContract.Card.COLUMN_IMAGE_URL + " text not null);";

	/**
	 * Create a card in the database with the given values.
	 */
	private static Card createCard(SQLiteDatabase db, String name, String mana, Integer cmc, String text,
			String type, String flavor, Integer power, Integer toughness,
			CardSet set, CardRarity rarity, Integer number,
			String artist, Integer multiverseId, String imageUrl) {
		
		ContentValues values = new ContentValues();
		values.put(DraftContract.Card.COLUMN_NAME, name);
		values.put(DraftContract.Card.COLUMN_MANA, mana);
		values.put(DraftContract.Card.COLUMN_CMC, cmc);
		values.put(DraftContract.Card.COLUMN_TYPE, type);
		values.put(DraftContract.Card.COLUMN_TEXT, text);
		values.put(DraftContract.Card.COLUMN_FLAVOR, flavor);
		values.put(DraftContract.Card.COLUMN_POWER, power);
		values.put(DraftContract.Card.COLUMN_TOUGHNESS, toughness);
		values.put(DraftContract.Card.COLUMN_SET, set.getCode());
		values.put(DraftContract.Card.COLUMN_RARITY, rarity.getId());
		values.put(DraftContract.Card.COLUMN_NUMBER, number);
		values.put(DraftContract.Card.COLUMN_ARTIST, artist);
		values.put(DraftContract.Card.COLUMN_MULTIVERSE_ID, multiverseId);
		values.put(DraftContract.Card.COLUMN_IMAGE_URL, imageUrl);

		long insertId = db.insert(TABLE_CARD, null,
				values);
		Cursor cursor = db.query(TABLE_CARD,
				DraftContract.Card.ALL_COLUMNS, DraftContract.Card.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Card newCard = cursorToCard(cursor);
		cursor.close();
		return newCard;
	}
		
	private static Card cursorToCard(Cursor cursor) {
		
		//Pull card values from table row
		int index = 0;
		Long id = cursor.getLong(index);
		String name = cursor.getString(++index);
		String mana = cursor.getString(++index);
		Integer cmc = cursor.getInt(++index);
		String type = cursor.getString(++index);
		String text = cursor.getString(++index);
		String flavor = cursor.getString(++index);
		Integer power = cursor.getInt(++index);
		Integer toughness = cursor.getInt(++index);
		CardSet set = CardSet.getSet(cursor.getString(++index));
		CardRarity rarity = CardRarity.getRarity(cursor.getString(++index));
		Integer number = cursor.getInt(++index);
		String artist = cursor.getString(++index);
		Integer multiverseId = cursor.getInt(++index);
		String imageUrl = cursor.getString(++index);
		
		//Construct and return card
		return new Card(id, name, mana, cmc, type, text, flavor, power, toughness, set, rarity, number, artist, multiverseId, imageUrl);
	}
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		
		//Hardcode some card DB dumps like a pro.
		createCard(database,
				"Abrupt Decay",
				"{B}{G}",
				2,
				"Abrupt Decay can't be countered by spells or abilities.\n\nDestroy target nonland permanent with converted mana cost 3 or less.",
				"Instant",
				"The Izzet quickly suspended their policy of lifetime guarantees.",
				null,
				null,
				CardSet.RTR,
				CardRarity.RARE,
				141,
				"Svetlin Velinov",
				253561,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253561&type=card");
		createCard(database,
				"Aerial Predation", 
				"{2}{G}", 
				3, 
				"Destroy target creature with flying. You gain 2 life.",
				"Instant", 
				"In the towering trees of the Samok Stand and the predators that guard them, the might of the Ravnican wild has returned.", 
				null, 
				null, 
				CardSet.RTR, 
				CardRarity.COMMON, 
				113, 
				"BD", 
				289227,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=289227&type=card");
		createCard(database,
				"Angel of Serenity", 
				"{4}{W}{W}{W}", 
				7, 
				"Flying\n\nWhen Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.\n\nWhen Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.", 
				"Creature - Angel", 
				null, 
				5, 
				6, 
				CardSet.RTR,
				CardRarity.MYTHIC,
				1,
				"Aleksi Birclot",
				253627,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253627&type=card");
		createCard(database,
				"Annihilating Fire", 
				"{1}{R}{R}", 
				3, 
				"Annihilating Fire deals 3 damage to target creature or player. If a creature dealt damage this way would die this turn, exile it instead.", 
				"Instant", 
				"\"The most impressive performances can be done only once.\"\n\n-Mote, Rakdos madcap",
				null, 
				null, 
				CardSet.RTR,  
				CardRarity.COMMON, 
				85, 
				"Clint Cearley", 
				270801,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270801&type=card");
		createCard(database,
				"Aquus Steed", 
				"{3}{U}", 
				4, 
				"{2}{U}, {T}: Target creature gets -2/-0 until end of turn.", 
				"Creature - Beast", 
				"In water, it's as graceful as a dolphin. On land, it darts and jerks so unpredictably that few can ride it for long.", 
				1, 
				3, 
				CardSet.RTR,
				CardRarity.UNCOMMON, 
				29, 
				"Warren Mahy", 
				270361,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270361&type=card");
		createCard(database,
				"Archon of the Triumvirate",
				"{5}{W}{U}",
				7,
				"Flying\n\nWhenever Archon of the Triumvirate attacks, detain up to two target nonland permanents your opponents control. (Until your next turn, those permanents can't attack or block and their activated abilities can't be activated.)",
				"Creature - Archon",
				null,
				4,
				5,
				CardSet.RTR,
				CardRarity.RARE,
				142,
				"David Rapoza",
				253603,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253603&type=card");
		createCard(database,
				"Archweaver",
				"{5}{G}{G}",
				7,
				null,
				"Creature - Spider",
				"The silk of the archweavers adds structural integrity to otherwise unstable Izzet building sites.",
				5,
				5,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				114,
				"Jason Felix",
				253601,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253601&type=card");
		createCard(database,
				"Armada Wurm",
				"{2}{G}{G}{W}{W}",
				6,
				"Trample\n\nWhen Armada Wurm enters the battlefield, put a 5/5 green Wurm creature token with trample onto the battlefield.",
				"Creature - Wurm",
				null,
				5,
				5,
				CardSet.RTR,
				CardRarity.MYTHIC,
				143,
				"Volkan Baga",
				253587,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253587&type=card");
		createCard(database,
				"Armory Guard",
				"{3}{W}{W}",
				4,
				"Armory Guard has vigilance as long as you control a Gate.",
				"Creature - Giant Soldier",
				"The Dimir agents watched from the shadows. \"Eight hours, and I've yet to see him blink,\" Nefara hissed. \"I suggest we find another way in.\"",
				2,
				5,
				CardSet.RTR,
				CardRarity.COMMON,
				2,
				"Karl Kopinski",
				253570,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253570&type=card");
		createCard(database,
				"Arrest",
				"{2}{W}",
				3,
				"Target creature gets +4/-4 until end of turn.",
				"Instant",
				"\"Finally, a weapon the Boros can't confiscate!\"\n\n-Juri, proprietor of the Juri Revue",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				144,
				"Raymond Swanland",
				253579,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253579&type=card");
		createCard(database,
				"Ash Zealot",
				"{R}{R}",
				2,
				"First strike, haste\n\nWhenever a player casts a spell from a graveyard, Ash Zealot deals 3 damage to that player",
				"Creature - Human Warrior",
				"Smarter than death? Let's see you outsmart my mace, necromancer!",
				2,
				2,
				CardSet.RTR,
				CardRarity.RARE,
				86,
				"Eric Deschamps",
				253623,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253623");
		createCard(database,
				"Assassin's Strike",
				"{4}{B}{B}",
				6,
				"Destroy target creature. Its controller discards a card.",
				"Sorcery",
				"When Selesnya missionaries moved into the Shanav Quarter, they faced scorn, ridicule, and ultimately martyrdom.",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				57,
				"Chase Stone",
				289216,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=289216");
		createCard(database,
				"Auger Spree",
				"{1}{B}{R}",
				3,
				"Target creature gets +4/-4 until end of turn.",
				"Instant",
				"\"Finally, a weapon the Boros can't confiscate!\"\n\n-Juri, proprietor of the Juri Revue",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				144,
				"Raymond Swanland",
				253579,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253579");
		createCard(database,
				"Avenging Arrow", 
				"{2}{W}",
				3,
				"Destroy target creature that dealt damage this turn.",
				"Instant",
				"\"Forgive the theft. Punish the deception.\"\n\n-Alcarus, Selesnya archer",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				4,
				"James Ryman",
				265410,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=265410&type=card");
		createCard(database,
				"Axebane Guardian",
				"{2}{G}",
				3,
				"Defender\n\n{T}: Add X mana in any combination of colors to your mana pool, where X is the number of creatures with defender you control.",
				"Creature - Human Druid",
				"Only a worthy few are permitted within the sentient forest known as Axebane.",
				0,
				3,
				CardSet.RTR,
				CardRarity.COMMON,
				115,
				"Slawomir Maniak",
				253556,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253556&type=card");
		createCard(database,
				"Axebane Stag",
				"{T}{G}",
				7,
				null,
				"Creature - Elk",
				"\"When the spires have burned and the cobblestones are dust, he will take his rightful place as king of the wilds.\"\n\n-Kirce, Axebane guardian",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				116,
				"Martina Pilcerova",
				265383,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=265383&type=card");
		createCard(database,
				"Azorius Arrester",
				"{1}{W}",
				2,
				"When Azorius Arrester enters the battlefield, detain target creature an opponent controls. (Until your next turn, that creature can't attack or block and its activated abilities can't be activated.)",
				"Creature - Human Soldier",
				"\"You have the right to remain silent. Mostly because I tire of your excuses.\"",
				2,
				1,
				CardSet.RTR,
				CardRarity.COMMON,
				5,
				"Wayn Reynolds",
				270972,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270972&type=card");
		createCard(database,
				"Azorius Charm",
				"{W}{U}",
				2,
				"Choose one - Creatures you control gain lifelink until end of turn; or draw a card; or put target attacking or blocking creature on top of its owner's library.",
				"Instant",
				"\"The rules of logic and order have already made the choice for you.\"\n\n-Isperia",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				145,
				"Zoltan Boros",
				270962,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270962&type=card");
		createCard(database,
				"Azorius Guildgate",
				null,
				null,
				"Azorius Guildgate enters the battlefield tapped.\n\n{T}: Add {W} or {U} to your mana pool.",
				"Land - Gate",
				"Enter the Senate, the seat of justice and the foundation of Ravnican society.",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				237,
				"Drew Baker",
				270966,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270966&type=card");
		createCard(database,
				"Azorius Justiciar",
				"{2}{W}{W}",
				4,
				"When Azorius Justiciar enters the battlefield, detain up to two target creatures your opponents control. (Until your next turn, those creatures can't attack or block and their activated abilities can't be activated.)",
				"Creature - Human Wizard",
				"\"Your potential to commit a crime warrants further investigation.\"",
				2,
				2,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				6,
				"Chris Rahn",
				270795,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=270795");
		createCard(database,
				"Azorius Keyrune",
				"{3}",
				3,
				"{T}: Add {W} or {U} to your mana pool.\n\n{W}{U}: Azorius Keyrune becomes a 2/2 white and blue Bird artifact creature with flying until end of turn.",
				"Artifact",
				"\"The higher the mind soars, the greater its understanding of the law.\"\n\n-Isperia",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				225,
				"Daniel Ljunggren",
				253519,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253519");
		createCard(database,
				"Azor's Elocutors",
				"{3}{W/U}{W/U}",
				5,
				"At the beginning of your upkeep, put a filibuster counter on Azor's Elocutors. Then if Azor's Elocutors has five or more filibuster counters on it, you win the game.\n\nWhenever a source deals damage to you, remove a filibuster counter from Azor's Elocutors.",
				"Creature - Human Advisor",
				null,
				3,
				5,
				CardSet.RTR,
				CardRarity.RARE,
				210,
				"Johannes Voss",
				265418,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=265418");
		createCard(database,
				"Batterhorn",
				"{4}{R}",
				5,
				"When Batterhorn enters the battlefield, you may destroy target artifact.",
				"Creature - Beast",
				"Novice shopkeeps spend hours deciding how best to display their wares. Veterans focus on portability.",
				4,
				3,
				CardSet.RTR,
				CardRarity.COMMON,
				87,
				"Dave Kendall",
				265399,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=265399&type=card");
		createCard(database,
				"Bazaar Krovod",
				"{4}{W}",
				5,
				"Whenever Bazaar Krovod attacks, another target attacking creature gets +0/+2 until end of turn. Untap that creature.",
				"Creature - Beast",
				"The Hauler's Collective wields great influence over the Ravnican merchant class. Without it, little moves in the city.",
				2,
				5,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				7,
				"Lars Grant-West",
				253589,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253589");
		createCard(database,
				"Bellows Lizard",
				"{R}",
				1,
				"{1}{R}: Bellows Lizard gets +1/+0 until end of turn.",
				"Creature - Lizard",
				"As the price of wood and coal rose, smiths found creative ways to keep their forges burning.",
				1,
				1,
				CardSet.RTR,
				CardRarity.COMMON,
				88,
				"Jack Wang",
				289214,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=289214&type=card");
		createCard(database,
				"Blistercoil Weird",
				"{U/R}",
				1,
				"Whenever you cast an instant or sorcery spell, Blistercoil Weird gets +1/+1 until end of turn. Untap it.",
				"Creature - Weird",
				"Azorius lawmages would like to outlaw the creation of weirds, but first they'd have to settle their long-standing debate on how to classify them.",
				1,
				1,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				211,
				"Dan Scott",
				289222,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=289222");
		createCard(database,
				"Blood Crypt",
				null,
				0,
				"({T}: Add {B} or {R} to your mana pool.)\n\nAs Blood Crypt enters the battlefield, you may pay 2 life. If you don't, Blood Crypt enters the battlefield tapped.",
				"Land - Swamp Mountain",
				"Where the dead serve as diversion, decor, and dessert.",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				238,
				"Vincent Proce",
				253683,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253683");
		createCard(database,
				"Bloodfray Giant",
				"2{R}{R}",
				4,
				"Trample\n\nUnleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)",
				"Creature - Giant",
				"The star performer of the Juri Revue, his shows have fans and blood spilling into the street.",
				4,
				3,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				89,
				"Steve Argyle",
				270785,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=270785");
		createCard(database,
				"Blustersquall",
				"{U}",
				1,
				"Tap target creature you don't control.\n\nOverload  (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of \"target\" with \"each.\")",
				"Instant",
				"Weather is more predictable than the Izzet.",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				30,
				"Willian Murai",
				253582,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253582");
		createCard(database,
				"Brushstrider",
				"1{G}",
				2,
				"Vigilance",
				"Creature - Beast",
				"Magistrate Ludy agreed to designate land for the brushstriders only after several broken windows and dozens of missing blini-cakes.",
				3,
				1,
				CardSet.RTR,
				CardRarity.COMMON,
				117,
				"Raoul Vitale",
				265377,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=265377");
		createCard(database,
				"Call of the Conclave",
				"{G}{W}",
				2,
				"Put a 3/3 green Centaur creature token onto the battlefield.",
				"Sorcery",
				"Centaurs are sent to evangelize in Gruul territories where words of war speak louder than prayers of peace.",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				146,
				"Terese Nielsen",
				261322,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=261322");
		createCard(database,
				"Cancel",
				"1{U}{U}",
				3,
				"Counter target spell.",
				"Instant",
				"\"It is forbidden. Asking why is irrelevant.\"\n\n-Sergiu, Opal Lake magistrate",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				31,
				"Karl Kopinski",
				265403,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=265403");
		createCard(database,
				"Carnival Hellsteed",
				"4{B}{R}",
				6,
				"First strike, haste\n\nUnleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)",
				"Creature - Nightmare Horse",
				"Its favorite treats are candied hands and sweet hearts.",
				5,
				4,
				CardSet.RTR,
				CardRarity.RARE,
				147,
				"Chase Stone",
				270963,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=270963");
		createCard(database,
				"Catacomb Slug",
				"4{B}",
				5,
				null,
				"Creature - Slug",
				"\"The entire murder scene was covered in dripping, oozing slime. No need for a soothsayer to solve that one.\"\n\n-Pel Javya, Wojek investigator",
				2,
				6,
				CardSet.RTR,
				CardRarity.COMMON,
				58,
				"Nils Hamm",
				253525,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253525");
		createCard(database,
				"Centaur Healer",
				"1{G}{W}",
				3,
				"When Centaur Healer enters the battlefield, you gain 3 life.",
				"Creature - Centaur Cleric",
				"Instructors at the Kasarna training grounds are capable healers in case their students fail to grasp the subtleties of combat.",
				3,
				3,
				CardSet.RTR,
				CardRarity.COMMON,
				148,
				"Mark Zug",
				253654,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253654");
		createCard(database,
				"Centaur's Herald",
				"G",
				1,
				"{2}{G}, Sacrifice Centaur's Herald: Put a 3/3 green Centaur creature token onto the battlefield.",
				"Creature - Elf Scout",
				"The farther they go from Vitu-Ghazi, the less willing the crowd is to part for them.",
				0,
				1,
				CardSet.RTR,
				CardRarity.COMMON,
				118,
				"Howard Lyon",
				265387,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=265387");
		createCard(database,
				"Chaos Imps",
				"{4}{R}{R}",
				6,
				"Flying\n\nUnleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)\n\nChaos Imps has trample as long as it has a +1/+1 counter on it.",
				"Creature - Imp",
				"null",
				6,
				5,
				CardSet.RTR,
				CardRarity.RARE,
				90,
				"Tyler Jacobson",
				270954,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=270954");
		createCard(database,
				"Chemister's Trick",
				"{U}{R}",
				2,
				"Target creature you don't control gets -2/-0 until end of turn and attacks this turn if able.\n\nOverload {3}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of \"target\" with \"each.\")",
				"Instant",
				null,
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				149,
				"Christopher Moeller",
				290524,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=290524");
		createCard(database,
				"Chorus of Might",
				"{3}{G}",
				4,
				"Until end of turn, target creature gets +1/+1 for each creature you control and gains trample.",
				"Instant",
				"\"In each of us is the strength of all of us.\"\n\n�Trostani",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				119,
				"Chorus of Might",
				265373,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=265373");
		createCard(database,
				"Chromatic Lantern",
				"{3}",
				3,
				"Lands you control have \"{T}: Add one mana of any color to your mana pool.\"\n\n{T}: Add one mana of any color to your mana pool.",
				"Artifact",
				"Dimir mages put the lanterns to good use, creating shapeshifters and sleeper agents from mana foreign to them.",
				null,
				null,
				CardSet.RTR,
				CardRarity.RARE,
				226,
				"Jung Park",
				290542,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=290542");
		createCard(database,
				"Chronic Flooding",
				"{1}{U}",
				2,
				"Enchant land\n\nWhenever enchanted land becomes tapped, its controller puts the top three cards of his or her library into his or her graveyard.",
				"Enchantment - Aura",
				"With the Izzet occupied elsewhere, many of their public works fell into disrepair.",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				32,
				"Scott Chou",
				270786,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=270786");
		createCard(database,
				"Civic Saber",
				"{1}",
				1,
				"Equipped creature gets +1/+0 for each of its colors.\n\nEquip {1}",
				"Artifact - Equipment",
				"Those without a guild signet often display a different form of protection.",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				227,
				"Jung Park",
				253619,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253619");
		createCard(database,
				"Cobblebrute",
				"{3}{R}",
				4,
				null,
				"Creature � Elemental",
				"The most ancient streets take on a life of their own. A few have decided to move to nicer neighborhoods.",
				5,
				2,
				CardSet.RTR,
				CardRarity.COMMON,
				91,
				"Eytan Zana",
				265369,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=265369");
		createCard(database,
				"Codex Shredder",
				"{1}",
				1,
				"{T}: Target player puts the top card of his or her library into his or her graveyard.\n\n{5}, {T}, Sacrifice Codex Shredder: Return target card from your graveyard to your hand.",
				"Artifact",
				"flavor",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				228,
				"Jason Felix",
				253635,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253635");
		createCard(database,
				"Collective Blessing",
				"{3}{G}{G}{W}",
				6,
				"Creatures you control get +3/+3.",
				"Enchantment",
				"Senators of Azorius often hired agents to spy on the Selesnya. They were told to record every spore and root they saw, as each could become a deadly foe.",
				null,
				null,
				CardSet.RTR,
				CardRarity.RARE,
				150,
				"Svetlin Velinov",
				290528,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=290528");
		createCard(database,
				"Common Bond",
				"{1}{G}{W}",
				3,
				"Put a +1/+1 counter on target creature.\n\nPut a +1/+1 counter on target creature.",
				"Instant",
				"Wolf riders hone their skills traversing the perilous rooftops, each dizzying step bringing elf and wolf closer together.",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				151,
				"Raymond Swanland",
				253565,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253565");
		createCard(database,
				"Concordia Pegasus",
				"{1}{W}",
				2,
				"Flying",
				"Creature � Pegasus",
				"\"A kick from its hooves is like a bolt of lightning. I'd know. I've been hit by both.\"\n\n�Rencz, Izzet chemister's aide",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				8,
				"Winona Nelson",
				253558,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253558");
		createCard(database,
				"Conjured Currency",
				"{5}{U}",
				6,
				"At the beginning of your upkeep, you may exchange control of Conjured Currency and target permanent you neither own nor control.",
				"Enchantment",
				"A bargain in Keyhole Downs is always too good to be true.",
				null,
				null,
				CardSet.RTR,
				CardRarity.RARE,
				33,
				"Steve Argyle",
				253606,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253606");
		createCard(database,
				"Corpsejack Menace",
				"{2}{B}{G}",
				4,
				"Creature � Fungus",
				"If one or more +1/+1 counters would be placed on a creature you control, twice that many +1/+1 counters are placed on it instead.",
				"Weakness is not in the nature of the Swarm.",
				4,
				4,
				CardSet.RTR,
				CardRarity.RARE,
				152,
				"Chris Rahn",
				253533,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253533");
		createCard(database,
				"Counterflux",
				"{U}{U}{R}",
				3,
				"Counterflux can't be countered by spells or abilities.\n\nCounter target spell you don't control.\n\nOverload {1}{U}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of \"target\" with \"each.\")",
				"Instant",
				null,
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				153,
				"Scott M. Fischer",
				253524,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=253524");
		
		/*
		createCard(database,
				"name",
				"manacost",
				0,
				"rulestext",
				"type",
				"flavor",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				000,
				"artist",
				000000,
				"http://gatherer.wizards.com/Handlers/Image.ashx?type=card&multiverseid=______");
		 */
	}

	//TODO: Integrate switch statement for DB Upgrades
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(CardTable.class.getName(), "Upgrading database from version "
		        + oldVersion + " to " + newVersion
		        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
	    onCreate(database);
	}
}

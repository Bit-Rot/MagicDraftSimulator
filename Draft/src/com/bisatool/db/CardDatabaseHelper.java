package com.bisatool.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.draft.CardRarity;
import com.example.draft.CardSet;

public class CardDatabaseHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "com.bisatool.db";
	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_CARD = "t_card";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_MANA = "mana";
	public static final String COLUMN_CMC = "cmc";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_TEXT = "text";
	public static final String COLUMN_FLAVOR = "flavor";
	public static final String COLUMN_POWER = "power";
	public static final String COLUMN_TOUGHNESS = "toughness";
	public static final String COLUMN_SET = "expansion";
	public static final String COLUMN_RARITY = "rarity";
	public static final String COLUMN_NUMBER = "number";
	public static final String COLUMN_ARTIST = "artist";
	public static final String COLUMN_MULTIVERSE_ID = "multiverse_id";
	public static final String COLUMN_IMAGE_URL = "image_url";
	
	private static final String DATABASE_CREATE = "create table " + TABLE_CARD + "(" +
		COLUMN_ID + " integer primary key autoincrement, " +
		COLUMN_NAME + " text not null, " +
		COLUMN_MANA + " text, " +
		COLUMN_CMC + " text, " +
		COLUMN_TYPE + " text not null, " +
		COLUMN_TEXT + " text, " +
		COLUMN_FLAVOR + " text, " +
		COLUMN_POWER + " text, " +
		COLUMN_TOUGHNESS + " text, " +
		COLUMN_SET + " text not null, " +
		COLUMN_RARITY + " text not null, " +
		COLUMN_NUMBER + " text not null, " +
		COLUMN_ARTIST + " text not null, " +
		COLUMN_MULTIVERSE_ID + " text not null, " +
		COLUMN_IMAGE_URL + " text not null);";

	
	public CardDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
		CardDatabase cardDatabase = CardDatabase.getInstance();
		
		//Hardcode some card DB dumps like a pro.
		cardDatabase.createCard(db,
				"Abrupt Decay",
				"{B}{G}",
				2,
				"Abrupt Decay can't be countered by spells or abilities.\nDestroy target nonland permanent with converted mana cost 3 or less.",
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
		cardDatabase.createCard(db,
				"Aerial Predation", 
				"2{G}", 
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
		cardDatabase.createCard(db,
				"Angel of Serenity", 
				"4{W}{W}{W}", 
				7, 
				"Flying\nWhen Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.\nWhen Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.", 
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
		cardDatabase.createCard(db,
				"Annihilating Fire", 
				"1{R}{R}", 
				3, 
				"Annihilating Fire deals 3 damage to target creature or player. If a creature dealt damage this way would die this turn, exile it instead.", 
				"Instant", 
				"\"The most impressive performances can be done only once.\"\n-Mote, Rakdos madcap",
				null, 
				null, 
				CardSet.RTR,  
				CardRarity.COMMON, 
				85, 
				"Clint Cearley", 
				270801,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=270801&type=card");
		cardDatabase.createCard(db,
				"Aquus Steed", 
				"3{U}", 
				4, 
				null, 
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
		cardDatabase.createCard(db,
				"Archon of the Triumvirate",
				"5{W}{U}",
				7,
				"Flying\nWhenever Archon of the Triumvirate attacks, detain up to two target nonland permanents your opponents control. (Until your next turn, those permanents can't attack or block and their activated abilities can't be activated.)",
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
		cardDatabase.createCard(db,
				"Archweaver",
				"5{G}{G}",
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
		cardDatabase.createCard(db,
				"Armada Wurm",
				"2{G}{G}{W}{W}",
				6,
				"Trample\nWhen Armada Wurm enters the battlefield, put a 5/5 green Wurm creature token with trample onto the battlefield.",
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
		cardDatabase.createCard(db,
				"Armory Guard",
				"3{W}{W}",
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
		cardDatabase.createCard(db,
				"Arrest",
				"2{W}",
				3,
				"Target creature gets +4/-4 until end of turn.",
				"Instant",
				"\"Finally, a weapon the Boros can't confiscate!\"\n-Juri, proprietor of the Juri Revue",
				null,
				null,
				CardSet.RTR,
				CardRarity.UNCOMMON,
				144,
				"Raymond Swanland",
				253579,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=253579&type=card");
		cardDatabase.createCard(db,
				"Avenging Arrow", 
				"2{W}",
				3,
				"Destroy target creature that dealt damage this turn.",
				"Instant",
				"\"Forgive the theft. Punish the deception.\"\n-Alcarus, Selesnya archer",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				4,
				"James Ryman",
				265410,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=265410&type=card");
		cardDatabase.createCard(db,
				"Axebane Guardian",
				"2{G}",
				3,
				"Defender\n{Tap}: Add X mana in any combination of colors to your mana pool, where X is the number of creatures with defender you control.",
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
		cardDatabase.createCard(db,
				"Axebane Stag",
				"6{G}",
				7,
				null,
				"Creature - Elk",
				"\"When the spires have burned and the cobblestones are dust, he will take his rightful place as king of the wilds.\"\n-Kirce, Axebane guardian",
				null,
				null,
				CardSet.RTR,
				CardRarity.COMMON,
				116,
				"Martina Pilcerova",
				265383,
				"http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=265383&type=card");
		cardDatabase.createCard(db,
				"Azorius Arrester",
				"1{W}",
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
		cardDatabase.createCard(db,
				"Azorius Guildgate",
				null,
				0,
				"Azorius Guildgate enters the battlefield tapped.\n{Tap}: Add {W} or {U} to your mana pool.",
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
		cardDatabase.createCard(db,
				"Batterhorn",
				"4{R}",
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
		cardDatabase.createCard(db,
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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(CardDatabaseHelper.class.getName(),
	    	"Upgrading database from version " + oldVersion + " to "
	        + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
	    onCreate(db);
	}
}

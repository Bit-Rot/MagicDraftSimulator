package com.werbsert.draftcommon.db;

import android.net.Uri;

/**
 * A Contract Class for defining an consistent interface to database/ContentProvider objects.
 * 
 * @author Andrew
 */
public final class DraftContract {
	public static final class TestDraftSet {
		public static final String AUTHORITY = "com.werbsert.testdraftset.testprovider";
		public static final String TYPE = "Card";
		public static final String SCHEME = "content";
		public static final Uri CARD_URI = Uri.parse(SCHEME + "://" + AUTHORITY + "/" + TYPE);
	}
	
	public static final class Card {

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
		
		static {
			ALL_COLUMNS = new String[] {
					Column.ID.getColumnName(),
					Column.NAME.getColumnName(),
					Column.MANA.getColumnName(),
					Column.CMC.getColumnName(),
					Column.TYPE.getColumnName(),
					Column.TEXT.getColumnName(),
					Column.FLAVOR.getColumnName(),
					Column.POWER.getColumnName(),
					Column.TOUGHNESS.getColumnName(),
					Column.SET.getColumnName(),
					Column.RARITY.getColumnName(),
					Column.NUMBER.getColumnName(),
					Column.ARTIST.getColumnName(),
					Column.MULTIVERSE_ID.getColumnName(),
					Column.IMAGE_URL.getColumnName()};
		}
		
		public static String[] ALL_COLUMNS;
		
		public enum Column {
			ID(DraftContract.Card.COLUMN_ID),
			NAME(DraftContract.Card.COLUMN_NAME),
			MANA(DraftContract.Card.COLUMN_MANA),
			CMC(DraftContract.Card.COLUMN_CMC),
			TYPE(DraftContract.Card.COLUMN_TYPE),
			TEXT(DraftContract.Card.COLUMN_TEXT),
			FLAVOR(DraftContract.Card.COLUMN_FLAVOR),
			POWER(DraftContract.Card.COLUMN_POWER),
			TOUGHNESS(DraftContract.Card.COLUMN_TOUGHNESS),
			SET(DraftContract.Card.COLUMN_SET),
			RARITY(DraftContract.Card.COLUMN_RARITY),
			NUMBER(DraftContract.Card.COLUMN_NUMBER),
			ARTIST(DraftContract.Card.COLUMN_ARTIST),
			MULTIVERSE_ID(DraftContract.Card.COLUMN_MULTIVERSE_ID),
			IMAGE_URL(DraftContract.Card.COLUMN_IMAGE_URL);
			
			private String m_columnName;
			
			private Column(String columnName) {
				m_columnName = columnName;
			}
			
			public String getColumnName() {
				return m_columnName;
			}
		}
	}
}

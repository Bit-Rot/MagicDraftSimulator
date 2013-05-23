package com.werbsert.rtr.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CardDatabaseHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "com.werbsert.rtr.db";
	public static final int DATABASE_VERSION = 1;
	
	public CardDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		CardTable.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		CardTable.onCreate(database);
	}
}

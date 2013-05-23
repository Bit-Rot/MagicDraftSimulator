package com.werbsert.rtr;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.werbsert.draftcommon.db.DraftContract;
import com.werbsert.draftcommon.log.DebugLog;
import com.werbsert.rtr.db.CardDatabaseHelper;
import com.werbsert.rtr.db.CardTable;

public class RtrProvider 
	extends ContentProvider {
	
	private CardDatabaseHelper m_cardDatabaseHelper;

	private static final int QUERY_CARD = 10;
	private static final int QUERY_CARD_ID = 20;
	private static final UriMatcher s_uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		s_uriMatcher.addURI(DraftContract.ReturnToRavnica.AUTHORITY, DraftContract.ReturnToRavnica.TYPE, QUERY_CARD);
		s_uriMatcher.addURI(DraftContract.ReturnToRavnica.AUTHORITY, DraftContract.ReturnToRavnica.TYPE + "/#", QUERY_CARD_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		DebugLog.log("entering delete()");
		
		SQLiteDatabase database = m_cardDatabaseHelper.getWritableDatabase();
		
		int uriType = s_uriMatcher.match(uri);
		int rowsDeleted = 0;
		switch (uriType) {
		case QUERY_CARD:
			rowsDeleted = database.delete(CardTable.TABLE_CARD, selection, selectionArgs);
		case QUERY_CARD_ID:
			String id = uri.getLastPathSegment();
			if (!TextUtils.isEmpty(selection)) {
				rowsDeleted = database.delete(CardTable.TABLE_CARD, DraftContract.Card.COLUMN_ID + " = " + id + " and " + selection, selectionArgs);
			} else {
				rowsDeleted = database.delete(CardTable.TABLE_CARD, DraftContract.Card.COLUMN_ID + " = " + id, null);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);

		DebugLog.log("exiting delete()");
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		DebugLog.log("entering insert()");
		SQLiteDatabase database = m_cardDatabaseHelper.getWritableDatabase();
		
		int uriType = s_uriMatcher.match(uri);
		Long insertId = -1L;
		switch (uriType) {
		case QUERY_CARD:
			database.insert(CardTable.TABLE_CARD, null, values);			
			break;
		case QUERY_CARD_ID:
			throw new IllegalArgumentException("Uri \"" + uri + "\" invalid");
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		DebugLog.log("exiting insert()");
	    return Uri.parse(DraftContract.ReturnToRavnica.TYPE + "/" + insertId);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
        DebugLog.log("Entering query()");

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(CardTable.TABLE_CARD);

        int uriType = s_uriMatcher.match(uri);
        switch (uriType) {
		case QUERY_CARD:
			break;
		case QUERY_CARD_ID:
			// Adding the ID to the original query
			queryBuilder.appendWhere(DraftContract.Card.COLUMN_ID + "=" + uri.getLastPathSegment());
		  break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        
        SQLiteDatabase database = m_cardDatabaseHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

		DebugLog.log("exiting query()");
        return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		DebugLog.log("entering update()");
		
		SQLiteDatabase database = m_cardDatabaseHelper.getWritableDatabase();
		
		int uriType = s_uriMatcher.match(uri);
		int rowsUpdated = 0;
		switch (uriType) {
		case QUERY_CARD:
			rowsUpdated = database.update(CardTable.TABLE_CARD, values, selection, selectionArgs);
			break;
		case QUERY_CARD_ID:
			String id = uri.getLastPathSegment();
			if (!TextUtils.isEmpty(selection)) {
				rowsUpdated = database.update(CardTable.TABLE_CARD, values, DraftContract.Card.COLUMN_ID + " = " + id + " and " + selection, selectionArgs);
			} else {
				rowsUpdated = database.update(CardTable.TABLE_CARD, values, DraftContract.Card.COLUMN_ID + " = " + id, null);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		DebugLog.log("exiting update()");
		return rowsUpdated;
	}

	@Override
	public boolean onCreate() {
		DebugLog.log("entering onCreate()");
		m_cardDatabaseHelper = new CardDatabaseHelper(getContext());
		DebugLog.log("exiting onCreate()");
		return true;
	}

	private void checkColumns(String[] projection) {
		String[] available = DraftContract.Card.ALL_COLUMNS;
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

			// Check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	  }
}

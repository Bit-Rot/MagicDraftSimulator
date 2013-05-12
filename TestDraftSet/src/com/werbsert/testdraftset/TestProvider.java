package com.werbsert.testdraftset;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class TestProvider 
	extends ContentProvider {

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		String[] testArray = {"_ID", "VALUE"};
		MatrixCursor cursor = new MatrixCursor(testArray);
		cursor.addRow(new Object[] {0, "TestValue0"});
		cursor.addRow(new Object[] {1, "TestValue1"});
		cursor.addRow(new Object[] {2, "TestValue2"});
		cursor.addRow(new Object[] {3, "TestValue3"});
		
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}
}

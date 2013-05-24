package com.werbsert.draft.util;

import android.os.Environment;

public class FileManager {
	
	private static FileManager s_instance;
	
	public static FileManager getInstance() {
		if (s_instance == null) {
			s_instance = new FileManager();
		}
		return s_instance;
	}
	
	public boolean fileExists(String filename) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Check whether we have read permissions for external storage.
	 * @return true if we have read permissions
	 */
	public boolean canRead() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}
	
	/**
	 * Check whether we have write permissions for external storage.
	 * @return true if we have write permissions
	 */
	public boolean canWrite() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * Check whether we have read and write permissions for external storage.
	 * @return true if we have read and write permissions
	 */
	public boolean canReadAndWrite() {
		//We can read and write in any state that we can read.
		return canWrite();
	}
	
	public void saveFile(String fileName) {
		throw new UnsupportedOperationException();
	}
}

package com.werbsert.draft.util;

import java.io.File;

import android.os.Environment;

public class CardImageManager {
	private static CardImageManager s_instance = null;
	
	public static CardImageManager getInstance() {
		if (s_instance == null) {
			s_instance = new CardImageManager();
		}
		return s_instance;
	}
	
	public File getImage(Integer multiverseId) {
		String fileName = Integer.toString(multiverseId) + ".png";
		File file = FileManager.getInstance().getFile(Environment.DIRECTORY_PICTURES, fileName);
		if (file.exists()) {
			return file;
		}
		
		return null;
	}
}

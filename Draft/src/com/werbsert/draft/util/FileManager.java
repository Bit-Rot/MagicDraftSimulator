package com.werbsert.draft.util;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

import com.werbsert.draft.DraftSimulatorApplication;

public class FileManager {
	
	private static FileManager s_instance;
	
	public static FileManager getInstance() {
		if (s_instance == null) {
			s_instance = new FileManager();
		}
		return s_instance;
	}
	
	/**
	 * Retrieves a handle to the application's external file storage.
	 * 
	 * @param directory (optional) A constant value determining which subfolder of the application's.
	 * 			external storage to retrieve a handle to.  Eg, <pre>Environment.DIRECTORY_MUSIC</pre>
	 * 			or <pre>Environment.DIRECTORY_PICTURES</pre>.  Pass null to receive a handle to the
	 * 			root directory.
	 * 			Uses the application's root storage directory if null.
	 * @return A handle to the application's external file storage.
	 */
	public File getExternalFilesDir(String directory) {
		return DraftSimulatorApplication.getContext().getExternalFilesDir(directory);
	}
	
	/**
	 * @param externalFileDirectory  A constant value determining which subfolder of the application's
	 * 			external storage to retrieve a handle to.  Eg, <pre>Environment.DIRECTORY_MUSIC</pre>
	 * 			or <pre>Environment.DIRECTORY_PICTURES</pre>.  Pass null to receive a handle to the
	 * 			root directory.
	 * @param fileName the name of the file we wish to retrieve
	 * @return null if the file or directory do not exist or were specified incorrectly
	 */
	public File getFile(String externalFileDirectory, String fileName) {
		File directory = getExternalFilesDir(externalFileDirectory);
		return new File(directory, fileName);
	}

	/**
	 * @param directory A handle to the directory in which the file with the given fileName is.
	 * @param fileName the name of the file we wish to retrieve
	 * @return null if the file or directory do not exist or were specified incorrectly
	 *
	public File getFile(File directory, String fileName) {
		return DraftSimulatorApplication.getContext().getFileStreamPath(directory.getPath() + fileName);
	}
	*/
	
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

	/**
	 * Saves the given file with the given file name in external storage.
	 * @param file the file to save
	 * @param directory (optional) A constant value determining which subfolder of the application's.
	 * 			Uses the application's root storage directory if null.
	 * @param fileName the name of the file to save.
	 */
	public void saveBitmapAsPng(Bitmap bitmap, String fileName) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), fileName));
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {}
			}
		}
	}
}

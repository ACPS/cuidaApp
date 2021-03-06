package com.nerdcore.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class ImageUitls {
	public static String saveOnDisk(Bitmap imagen) {
		
		String the_path = Environment.getExternalStorageDirectory()
				+ File.separator + "cuidaApp";
		String uid = UUID.randomUUID().toString();
		String the_file = the_path + File.separator + uid + ".png";

		OutputStream fout = null;
		
		boolean success = (new File(the_path)).mkdir(); 
        if (!success) {
            Log.i("directory not created", "directory not created");
        } else {
            Log.i("directory created", "directory created");
        }
        
      
		try {
			Log.v("Path", the_file);
			fout = new FileOutputStream(the_file);
			imagen.compress(Bitmap.CompressFormat.PNG, 100, fout);
			fout.flush();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return the_file;
	}
}

/**
 * 
 */
package com.nerdcore.utils;

import android.content.pm.PackageManager;

import com.nerdcore.logs.Trace;

import cuidaApp.MainApplication;

/**
 * @author Jorge
 * 
 */
public class CheckPermissionsUtils {

	private static final String TAG = "class CheckPermissionsUtils";

	/**
	 * It's verify for a specific permission is enabled or disabled
	 * 
	 * @param permission
	 * @return
	 */
	public static boolean is(String permission) {
		try {
			// return packageManager.checkPermission(permission, MainApplication
			// .getContext().getPackageName()) ==
			// PackageManager.PERMISSION_GRANTED;
			boolean result = MainApplication.getContext()
					.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
			// Trace.i(TAG, "Permission " + permission + " > " + result);
			return result;
		} catch (Exception e) {
			Trace.e(TAG, "Can't check: " + permission);
		}
		return false;
	}

}

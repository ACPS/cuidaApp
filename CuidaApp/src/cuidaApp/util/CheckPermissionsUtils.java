/**
 * 
 */
package cuidaApp.util;

import android.content.pm.PackageManager;

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
			boolean result = MainApplication.getContext()
					.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
			return result;
		} catch (Exception e) {
			Trace.e(TAG, "Can't check: " + permission);
		}
		return false;
	}

}

/**
 * 
 */
package com.nerdcore.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import android.Manifest.permission;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.nerdcore.logs.Trace;

import cuidaApp.MainApplication;

/**
 * @author Jorge
 * 
 */
public class PhoneUtils {

	private static final String TAG = "class PhoneUtils";
	private static TelephonyManager telephonyManager;

	private static TelephonyManager getTelephonyManager() {
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) MainApplication.getContext()
					.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return telephonyManager;
	}
	
	
	/**
	 * Return a HashMap<String, String> with relevant information about the
	 * phone
	 * 
	 * @return
	 */
	public static HashMap<String, String> readPhoneData() {
		HashMap<String, String> phone_data = null;
		if (CheckPermissionsUtils.is(permission.READ_PHONE_STATE)) {

			TelephonyManager tm = getTelephonyManager();
			phone_data = new HashMap<String, String>();
			phone_data.put("imei", tm.getDeviceId());
			phone_data.put("version", tm.getDeviceSoftwareVersion());
			phone_data.put("country", tm.getNetworkCountryIso());
			phone_data.put("number", tm.getSimSerialNumber());
			phone_data.put("operator", tm.getNetworkOperatorName());
			phone_data.put("sim_country", tm.getSimCountryIso());
//			tm.getSimSerialNumber();

			for (Entry<String, String> entry : phone_data.entrySet()) {
				Trace.i(TAG,
						String.format("%s > %s", entry.getKey(),
								entry.getValue()));
			}
		} else {
			Trace.e(TAG, "User does't has READ_PHONE_STATE permission");
		}
		return phone_data;
	}
	

    
}

package cuidaApp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import restful.DoRest;
import restful.DoRest.Verbs;
import restful.DoRestEventListener;
import android.app.Activity;
import android.content.Context;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

import cuidaApp.common.CommonGlobals;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

public class EmailConfirmationController {

	private final String TAG = "EmailConfirmationController ";
	private Context context;
	
	
	

	public EmailConfirmationController() {

	}

	public void check_code(Context context, String code) {
		this.context = context;
		boolean ok = true;

		if (code.trim().length() == 0) {
			CommonGlobals.showToast(context
					.getString(R.string.error_code_required),context);
			ok = false;
		}

		if (ok) {
			if (code.trim().length() != 4) {
				CommonGlobals.showToast(context
						.getString(R.string.error_code_invalid),context);
				ok = false;
			}
		}

		if (ok) {
			check_code(code);
		}
	}

	private void check_code(String code) {
		Trace.i(TAG, PreferencesController.getInstance().getPreferences("token"));
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("confirmation[code]", code));
		params.add(new BasicNameValuePair("confirmation[token]",
				PreferencesController.getInstance().getPreferences("token")));
		params.add(new BasicNameValuePair("confirmation[application_token]",
				AppConfig.PUSHER_APP_KEY));

		params.add(new BasicNameValuePair("application_token",
				AppConfig.PUSHER_APP_KEY));

		DoRest rest_verification = new DoRest(AppConfig.CONFIRMATION_USERS_URL,
				Verbs.POST, params);
		CommonGlobals.showProgess(context);
		
		rest_verification.setListener(new DoRestEventListener() {

			@Override
			public void onError() {

				// showCategoriesError();
				CommonGlobals.hideProgess();
				CommonGlobals.show_alert(context, context.getString(R.string.error_global));
			}

			@Override
			public void onComplete(int status, String json_data_string) {
				if (status == 200) {
					JSONObject response = null;
					try {
						response = new JSONObject(json_data_string);
						if (response.getBoolean("status")) {
							AppGlobal.getInstance().dispatcher.open(((Activity)context), "login", true);
							
						}else{
							
							JSONArray errors = response.getJSONArray("errors");
							CommonGlobals.hideProgess();
							CommonGlobals.show_alert(context, errors.get(0).toString());
						}
					} catch (Exception ex) {
						response = null;
						CommonGlobals.hideProgess();
						CommonGlobals.show_alert(context, context.getString(R.string.error_global));
					}
				} else {
					CommonGlobals.showToast("Error",context);
					CommonGlobals.hideProgess();
					CommonGlobals.show_alert(context, context.getString(R.string.error_global));
				}
			}
		});
		rest_verification.call();
	}

	
	

}

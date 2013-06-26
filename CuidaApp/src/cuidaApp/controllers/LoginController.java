package cuidaApp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
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

public class LoginController {

	private final String TAG = "LoginController";
	private Context context;
	
	

	public LoginController() {

	}

	

	

	public boolean Login(String email, String password) {
		boolean ok = false;

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("session[email]", email));
		params.add(new BasicNameValuePair("session[password]", password));
		
		Trace.i(TAG,AppConfig.SIGNIN_USERS_URL );
		CommonGlobals.showProgess(context);
		// params.add(new
		// BasicNameValuePair("application_token",AppConfig.PUSHER_APP_KEY));
		
		DoRest rest_login = new DoRest(AppConfig.SIGNIN_USERS_URL, Verbs.POST,
				params);

		rest_login.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Trace.i(TAG, "error1");
				CommonGlobals.hideProgess();
				CommonGlobals.show_alert(context, context.getString(R.string.activity_login_error));
			}

			@Override
			public void onComplete(int status, String json_data_string) {
				
				Trace.i(TAG, "onComplete");
				
				if (status == 200) {
					
					JSONObject response = null;
					JSONObject data;
					JSONObject session;
					
					try {
						response = new JSONObject(json_data_string);
						Trace.i(TAG, response.getBoolean("status") + "");
						if (response.getBoolean("status")) {
							

							data = response.getJSONObject("data");
							session = data.getJSONObject("session");
							String token = session.getString("auth_token");
							
							
							// String
							// token_expires=session.getString("token_expires");

							PreferencesController.getInstance().addPreferences(
									"token", token);
							PreferencesController.getInstance().addPreferences(
									"active", "true");
							
							CommonGlobals.hideProgess();
							AppGlobal.getInstance().dispatcher
									.open((Activity) context,											
											"main", true);
						} else {
							// AppGlobal.getInstance().dispatcher.open((Activity)context,
							// SplashscreenController.getInstance().sendActivity(),
							// false);
							JSONArray errors = response.getJSONArray("errors");
							Trace.i(TAG, errors.toString());
							CommonGlobals.hideProgess();
//							CommonGlobals.show_alert(context, errors.get(0).toString());
							AppGlobal.getInstance().dispatcher
							.open((Activity) context,											
									"main", true);
						}

					} catch (JSONException e) {
						Trace.i(TAG, e.getMessage());
						CommonGlobals.hideProgess();
						CommonGlobals.show_alert(context, context.getString(R.string.error_global));
					}
				} else {
					Trace.i(TAG, "error2");
					CommonGlobals.hideProgess();
					CommonGlobals.show_alert(context, context.getString(R.string.error_global));
				}
			}
		});
		
		rest_login.call();

		return ok;
	}

	public void check_information(Context con, String email, String password) {
		this.context = con;
		boolean ok = true;

		if (email.trim().length() == 0) {
			ok = false;
			CommonGlobals.showToast(context.getString(R.string.error_email_required),context);
		}

		if (ok) {
			ok = CommonGlobals.checkEmail(email);
			if (!ok) {
				CommonGlobals.showToast(context
						.getString(R.string.error_invalid_email),context);
			}
		}

		if (ok) {
			if (password.trim().length() == 0) {
				ok = false;
				CommonGlobals.showToast(context
						.getString(R.string.error_password_required),context);
			}
		}

		if (ok) {
			if (password.trim().length() < 6) {
				ok = false;
				CommonGlobals.showToast(context
						.getString(R.string.error_invalid_password),context);
			}
		}

		if (ok) {
			Login(email, password);
		}

	}

	


}

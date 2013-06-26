package cuidaApp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import restful.DoRest;
import restful.DoRest.Verbs;
import restful.DoRestEventListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

import cuidaApp.common.CommonGlobals;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

public class CodeProblemsController {

	
	private Context context;
	private final String TAG = "CodeProblemsController";

	
	public CodeProblemsController() {

	}

	public void sendCode(Context con, String email) {
		this.context=con;
		if (CommonGlobals.checkEmail(email)) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", email));

			DoRest rest_code_problems = new DoRest(
					AppConfig.FORWARD_CODE_URL, Verbs.POST, params);
			CommonGlobals.showProgess(context);
			rest_code_problems.setListener(new DoRestEventListener() {

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
								
								JSONObject data = response.getJSONObject("data");
								JSONObject confirmation = data
										.getJSONObject("confirmation");

								String token = confirmation.getString("token");
								String token_expires = confirmation
										.getString("token_expires");
								CommonGlobals.hideProgess();
								PreferencesController.getInstance().deletePreferences("token");
								PreferencesController.getInstance().addPreferences("token", token);
								PreferencesController.getInstance().addPreferences("token_expires", token_expires);
								//AppGlobal.getInstance().dispatcher.open((Activity) context, "emailconfirmation", true);
								showAlertMessageDialog(context);
							} else {

								JSONArray errors = response.getJSONArray("errors");
								Trace.i(TAG,
										"Error del servidor: " + errors.toString());
								CommonGlobals.hideProgess();
								CommonGlobals.show_alert(context, errors.get(0).toString());
								
							}
						} catch (Exception ex) {
							response = null;
							CommonGlobals.hideProgess();							
							CommonGlobals.show_alert(context, context.getString(R.string.error_global));
						}
					} else {
						CommonGlobals.hideProgess();
						CommonGlobals.show_alert(context, context.getString(R.string.error_global));
					}
				}
			});
			rest_code_problems.call();

		} else {
			CommonGlobals.showToast(context.getString(R.string.error_invalid_email),context);
		}
	}
	
	private void showAlertMessageDialog(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.activity_code_problems_alert_titlle));
		builder.setMessage(context.getString(R.string.activity_code_problems_alert_message));
		builder.setCancelable(true);

		final AlertDialog dlg = builder.create();
		dlg.show();

		dlg.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				AppGlobal.getInstance().dispatcher.open((Activity) context, "emailconfirmation", true);
			}
		});

		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				dlg.dismiss();
				t.cancel();
				AppGlobal.getInstance().dispatcher.open((Activity) context, "emailconfirmation", true);
			}
		}, 6000);
	}

	
}

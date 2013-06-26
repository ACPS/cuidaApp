/**
 * 
 */
package cuidaApp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
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

/**
 * @author LUSTER
 * 
 */
public class ForgetPasswordController {

	private static final String TAG = "ForgetPasswordController";


	public ForgetPasswordController() {

	}

	public void checkAndSendEmail(String email, Context context) {

		if (email.trim().length() == 0) {
			CommonGlobals.showToast(
					context.getString(R.string.error_email_required),
					context);
		} else {
			sendEmail(email, context);
		}

	}

	private void sendEmail(String email, final Context context) {

		if (CommonGlobals.checkEmail(email)) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", email));

			DoRest restSendEmail = new DoRest(AppConfig.RECOVERY_PASSWORD_URL,
					Verbs.POST, params);
			CommonGlobals.showProgess(context);
			restSendEmail.setListener(new DoRestEventListener() {

				@Override
				public void onError() {
					Trace.i(TAG, "error");
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

								JSONObject data = response
										.getJSONObject("data");
								JSONObject confirmation = data
										.getJSONObject("confirmation");

								String token = confirmation.getString("token");
								String token_expires = confirmation
										.getString("token_expires");

								Trace.i(TAG, token);
								Trace.i(TAG, token_expires);

								PreferencesController.getInstance()
										.addPreferences("token_forget_password", token);
								PreferencesController.getInstance()
										.addPreferences("token_forget_password_expires",
												token_expires);

								CommonGlobals.hideProgess();
								
								showAlertMessageDialog(context);


							} else {

								JSONArray errors = response
										.getJSONArray("errors");
								Trace.i(TAG,
										"Error del servidor: "
												+ errors.toString());
								CommonGlobals.hideProgess();
								CommonGlobals.show_alert(context, errors.get(0)
										.toString());
							}

						} catch (JSONException e) {
							response = null;
							Trace.i(TAG, "Catch: " + e.getMessage());
							CommonGlobals.hideProgess();
							CommonGlobals.show_alert(context, context.getString(R.string.error_global));
						}

					} else {
						CommonGlobals.hideProgess();
						CommonGlobals.show_alert(context, context.getString(R.string.error_global));
					}
				}

			});

			restSendEmail.call();
		} else {
			CommonGlobals.showToast(
					context.getString(R.string.error_invalid_email), context);
		}
	}
	private void showAlertMessageDialog(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.activity_forget_password_alert_titlle));
		builder.setMessage(context.getString(R.string.activity_forget_password_alert_message));
		builder.setCancelable(true);

		final AlertDialog dlg = builder.create();
		dlg.show();

		dlg.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
//				AppGlobal.getInstance().dispatcher.open((Activity) context,
//						"changepassword", true);
			}
		});

		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				dlg.dismiss();
				t.cancel();
				AppGlobal.getInstance().dispatcher.open((Activity) context,
						"changepassword", true);
			}
		}, 6000);
	}

}

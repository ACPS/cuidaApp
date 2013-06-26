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
import android.util.Log;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

import cuidaApp.common.CommonGlobals;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

/**
 * @author LUSTER
 * 
 */
public class RegisterController {


	private final String TAG = "RegisterController";
	private Context context;

	
	public RegisterController() {

	}

	public void sendRegister(String name, String last_name, String email,
			String password, String password_confirmation) {

		Trace.i(TAG, "entro");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user[name]", name));
		params.add(new BasicNameValuePair("user[last_name]", last_name));
		params.add(new BasicNameValuePair("user[email]", email));
		params.add(new BasicNameValuePair("user[password]", password));
		params.add(new BasicNameValuePair("user[password_confirmation]",
				password_confirmation));

	

		params.add(new BasicNameValuePair("application_token",
				AppConfig.PUSHER_APP_KEY));
		CommonGlobals.showProgess(context);
		DoRest rest_register = new DoRest(AppConfig.REGISTER_USERS_URL,
				Verbs.POST, params);

		rest_register.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Log.e(TAG, "Se presento un error al enviar el registro");
				// showCategoriesError();
				CommonGlobals.show_alert(context, context.getString(R.string.error_global));
				CommonGlobals.hideProgess();
			
			}

			@Override
			public void onComplete(int status, String json_data_string) {

				Trace.i(TAG, "Oncomplete");

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

							Trace.i(TAG, token);
							Trace.i(TAG, token_expires);
							PreferencesController.getInstance()
									.deletePreferences("token");
							PreferencesController.getInstance().addPreferences(
									"token", token);
							PreferencesController.getInstance().addPreferences(
									"token_expires", token_expires);

							CommonGlobals.hideProgess();

							showAlertMessageDialog();

						} else {

							JSONArray errors = response.getJSONArray("errors");

							CommonGlobals.hideProgess();
							CommonGlobals.show_alert(context, errors.get(0).toString());

							
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
		rest_register.call();
	}

	private void showAlertMessageDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.activity_register_alert_titlle));
		builder.setMessage(context.getString(R.string.activity_register_alert_message));
		builder.setCancelable(true);

		final AlertDialog dlg = builder.create();
		dlg.show();

		dlg.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				AppGlobal.getInstance().dispatcher.open((Activity) context,
						"emailconfirmation", true);
			}
		});

		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				dlg.dismiss();
				t.cancel();
				AppGlobal.getInstance().dispatcher.open((Activity) context,
						"emailconfirmation", true);
			}
		}, 6000);
	}

	public void check_information(Context context, String name, String last_name, String email,
			String password, String password_confirmation) {
		this.context= context;
		boolean ok=true;
		
		
		if(name.trim().length()==0){
			CommonGlobals.showToast(context.getString(R.string.error_name_requerired),context);
			ok=false;
		}
		
		if(ok){
			if(last_name.trim().length()==0){
				CommonGlobals.showToast(context.getString(R.string.error_name_requerired),context);
				ok=false;
				return;
			}
		}
		
		if(ok){
			if(email.trim().length()==0){
				CommonGlobals.showToast(context.getString(R.string.error_email_required),context);
				ok=false;
				return;
			}
		}
		
		if(ok){
			if(!CommonGlobals.checkEmail(email)){
				CommonGlobals.showToast(context.getString(R.string.error_invalid_email),context);
				ok=false;
				return;
			}
		}
		
		if(ok){
			if(password.trim().length()==0){
				CommonGlobals.showToast(context.getString(R.string.error_password_required),context);
				ok=false;
				return;
			}
		}
		
		if(ok){
			if(password.trim().length()<6){
				CommonGlobals.showToast(context.getString(R.string.error_invalid_password),context);
				ok=false;
				return;
			}
		}
		if(ok){
			if(!password.equals(password_confirmation)){
				CommonGlobals.showToast(context.getString(R.string.error_password_distint),context);
				ok=false;
				return;
			}
		}

		if (ok) {
			sendRegister(name, last_name, email, password,
					password_confirmation);
		}
	}
}

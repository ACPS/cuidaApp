/**
 * 
 */
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

/**
 * @author LUSTER
 *
 */
public class ChangePasswordController {
	
	
	private final String TAG = "ChangePasswordController";
	
	
	public ChangePasswordController(){
		
	}
	
	public void checkAndSendData(
			String confirmation_code, String password, String password_confirmation,  final Context context){
		
		String confirmation_token = PreferencesController.getInstance().getPreferences("token_forget_password");
		
		if(this.checkField(confirmation_code, 4)){
			if(this.checkField(password, 6)){
				if(this.checkField(password_confirmation, 6)){
					if(password.equals(password_confirmation)){
						//Falta preguntar si confirmation code ha expirado.
						sendData(confirmation_code, password, password_confirmation, confirmation_token, context);
					}else{
						CommonGlobals.showToast(context.getString(R.string.error_password_distint), context);
					}
				}else{
					CommonGlobals.showToast(context.getString(R.string.error_invalid_password), context);
				}
			}else{
				CommonGlobals.showToast(context.getString(R.string.error_invalid_password), context);
			}
		}else{
			CommonGlobals.showToast(context.getString(R.string.error_code_invalid), context);
		}
		
	}
	
	private boolean checkField(String field, int minLength){
		return (field != null) && (field.length() > minLength - 1); 
	}
	
	private void sendData(
			String confirmation_code, String password, String password_confirmation, 
			String confirmation_token, final Context context){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("checker[confirmation_code]", confirmation_code));
		params.add(new BasicNameValuePair("checker[confirmation_token]", confirmation_token));
		params.add(new BasicNameValuePair("checker[password]", password));
		params.add(new BasicNameValuePair("checker[password_confirmation]", password_confirmation));
		CommonGlobals.showProgess(context);
		DoRest restSendEmail = new DoRest(AppConfig.CHANGE_PASSWORD_URL,
				Verbs.POST, params);
		restSendEmail.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Trace.i(TAG, "ERROR");
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
							
							CommonGlobals.hideProgess();
							AppGlobal.getInstance().dispatcher.open((Activity) context, "login", true);

						} else {

							JSONArray errors = response.getJSONArray("errors");
							
							CommonGlobals.hideProgess();
							Trace.i(TAG,
									"Error del servidor: " + errors.toString());
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
		restSendEmail.call();
	}
	

}

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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

import cuidaApp.common.CommonGlobals;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

public class OptionsController {

	private static final String TAG = "UpdateUserController";

	
	public OptionsController() {

	}

	public void showUser(final EditText name, final EditText last_name,
			final Context context) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("auth_token", PreferencesController
				.getInstance().getPreferences("token")));

		DoRest showUser = new DoRest(AppConfig.SHOW_USER_URL, Verbs.POST,
				params);
		CommonGlobals.showProgess(context);
		Trace.e(TAG, "TOKEN: "
				+ PreferencesController.getInstance().getPreferences("token"));
		showUser.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Trace.i(TAG, "error");
				CommonGlobals.hideProgess();
				CommonGlobals.show_alert(context,
						context.getString(R.string.error_global));
			}

			@Override
			public void onComplete(int status, String json_data_string) {

				if (status == 200) {

					JSONObject response = null;

					try {

						response = new JSONObject(json_data_string);

						if (response.getBoolean("status")) {

							JSONObject data = response.getJSONObject("data");
							JSONObject user = data.getJSONObject("user");

							String name_value = user.getString("name");
							String last_name_value = user
									.getString("last_name");
							name.setText(name_value);
							last_name.setText(last_name_value);
							CommonGlobals.hideProgess();
							name.requestFocus();
							name.clearFocus();
							name.setSelection(name.getText().length());
								
						} else {

							JSONArray errors = response.getJSONArray("errors");

							CommonGlobals.hideProgess();
							CommonGlobals.show_alert(context, errors.get(0)
									.toString());
						}

					} catch (JSONException e) {
						response = null;
						Trace.i(TAG, "Catch: " + e.getMessage());
						CommonGlobals.hideProgess();
						CommonGlobals.show_alert(context,
								context.getString(R.string.error_global));
					}

				} else {
					CommonGlobals.hideProgess();
					CommonGlobals.show_alert(context,
							context.getString(R.string.error_global));
				}
			}

		});
		showUser.call();
	}

	public void logout(final Context context) {

		Trace.i(TAG, "logout");

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.dialog_logout_content)
				.setTitle(R.string.dialog_logout_title)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.dialog_logout_btn_confirmation,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {

								signout(context);
							}
						})
				.setNegativeButton(R.string.dialog_logout_btn_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								dialog.cancel();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();

	}

	public void signout(final Context context) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("auth_token", PreferencesController
				.getInstance().getPreferences("token")));

		DoRest signout = new DoRest(AppConfig.SIGN_OUT_URL, Verbs.POST, params);
		CommonGlobals.showProgess(context);

		signout.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Trace.i(TAG, "error");
				CommonGlobals.hideProgess();
				ManagerController.getInstance().reset();
				PreferencesController.getInstance().deleteAllPreferences();
				AppGlobal.getInstance().dispatcher.open((Activity)context, "login", true);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * alcaldiadebarranquilla.movilidad.android.restful.DoRestEventListener
			 * #onComplete(int, java.lang.String)
			 */
			@Override
			public void onComplete(int status, String json_data_string) {

				CommonGlobals.hideProgess();
				ManagerController.getInstance().reset();
				PreferencesController.getInstance().deleteAllPreferences();
				AppGlobal.getInstance().dispatcher.open((Activity)context, "login", true);

			}

		});

		signout.call();

	}

	public void updateUser(String name, String last_name,
			String password_actual, String newpassword,
			String repeatnewapassword, Context context) {

		boolean ok = true;
		if (name.trim().length() == 0) {
			ok = false;
			CommonGlobals.showToast(
					context.getString(R.string.error_name_requerired), context);
		}

		if (ok) {
			if (last_name.length() == 0) {
				ok = false;
				CommonGlobals.showToast(
						context.getString(R.string.error_last_name_requerired),
						context);
			}
		}
		if (ok) {
			if ((password_actual.trim().length() < 6)
					&& (password_actual.trim().length() > 0)) {
				ok = false;
				CommonGlobals.showToast(context
						.getString(R.string.error_invalid_password_actual),
						context);
			}
		}

		if (ok) {
			if ((newpassword.trim().length() == 0)
					&& (password_actual.trim().length() > 0)) {
				ok = false;
				CommonGlobals
						.showToast(
								context.getString(R.string.error_password_new_required),
								context);
			}
		}

		if (ok) {
			if ((newpassword.trim().length() < 6)
					&& (password_actual.trim().length() > 0)) {
				ok = false;
				CommonGlobals.showToast(context
						.getString(R.string.error_invalid_password_actual),
						context);
			}
		}

		if (ok) {
			if ((!newpassword.equals(repeatnewapassword))
					&& (password_actual.trim().length() > 0)) {
				ok = false;
				Trace.i(TAG, newpassword + " " + repeatnewapassword);
				CommonGlobals.showToast(
						context.getString(R.string.error_password_distint),
						context);
			}
		}

		if (ok) {
			if ((newpassword.trim().length() > 0)
					|| (repeatnewapassword.trim().length() > 0)) {
				if (password_actual.length() == 0) {
					ok = false;
					CommonGlobals
							.showToast(
									context.getString(R.string.error_password_actual_required),
									context);
				}
			}
		}

		if (ok) {
			UpdateUser(name, last_name, password_actual, newpassword,
					repeatnewapassword, context);
		}
	}

	public void UpdateUser(String name, String last_name,
			String password_actual, String newpassword,
			String repeatnewapassword, final Context context) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("auth_token", PreferencesController
				.getInstance().getPreferences("token")));
		params.add(new BasicNameValuePair("user[password_actual]",
				password_actual));
		params.add(new BasicNameValuePair("user[password]", newpassword));
		params.add(new BasicNameValuePair("user[password_confirmation]",
				repeatnewapassword));
		params.add(new BasicNameValuePair("user[name]", name));
		params.add(new BasicNameValuePair("user[last_name]", last_name));

		DoRest updateUser = new DoRest(AppConfig.UPDATE_USER_URL, Verbs.POST,
				params);
		CommonGlobals.showProgess(context);
		updateUser.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Trace.i(TAG, "error");
				CommonGlobals.hideProgess();
				CommonGlobals.show_alert(context,
						context.getString(R.string.error_global));
			}

			@Override
			public void onComplete(int status, String json_data_string) {

				if (status == 200) {

					JSONObject response = null;

					try {

						response = new JSONObject(json_data_string);

						if (response.getBoolean("status")) {
							CommonGlobals.hideProgess();

							CommonGlobals.show_alert_infor(
									context,
									context.getString(R.string.activity_options_success_update));
						} else {
							JSONArray errors = response.getJSONArray("errors");
							
							CommonGlobals.hideProgess();
							CommonGlobals.show_alert(context, errors.get(0).toString());
						}

					} catch (JSONException e) {
						response = null;
						Trace.i(TAG, "Catch: " + e.getMessage());
						CommonGlobals.hideProgess();
						CommonGlobals.show_alert(context,
								context.getString(R.string.error_global));
					}

				} else {
					CommonGlobals.hideProgess();
					CommonGlobals.show_alert(context,
							context.getString(R.string.error_global));
				}
			}

		});
		updateUser.call();
	}


}

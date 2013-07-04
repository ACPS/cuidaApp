package cuidaApp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.nerdcore.utils.ActivityDispatcher;

public class AppGlobal {

	private static AppGlobal instance;
	private ProgressDialog dialog;
	private AlertDialog alertDialog;
	public ActivityDispatcher dispatcher;
	public int answered_questions;
	public int Image;

	private AppGlobal() {

	}

	public void initialize(Context context) {

		dispatcher = new ActivityDispatcher();

		dispatcher.addHandler("login",
				"cuidaApp.views.Login");
		dispatcher.addHandler("main",
				"cuidaApp.views.MainActivity");
		dispatcher.addHandler("take",
				"cuidaApp.views.SendReport");
		dispatcher.addHandler("activos",
				"cuidaApp.views.Actives");
		dispatcher.addHandler("confirm",
				"cuidaApp.views.Confirm");
		dispatcher.addHandler("report",
				"cuidaApp.views.Report");
		dispatcher.addHandler("sending",
				"cuidaApp.views.Sending");
		dispatcher.addHandler("thanks",
				"cuidaApp.views.Thanks");
		dispatcher.addHandler("registrar",
				"cuidaApp.views.Registrar");
		dispatcher.addHandler("emailconfirmation",
				"cuidaApp.views.EmailConfirmation");
		dispatcher.addHandler("codeproblems",
				"cuidaApp.views.CodeProblems");
		dispatcher.addHandler("forgetpassword",
				"cuidaApp.views.ForgetPassword");
		dispatcher.addHandler("changepassword",
				"cuidaApp.views.ChangePassword");
		dispatcher.addHandler("options",
				"cuidaApp.views.Options");
		dispatcher.addHandler("category",
				"cuidaApp.views.ImageGridActivity");
	}

	public static synchronized AppGlobal getInstance() {
		if (instance == null) {
			instance = new AppGlobal();
		}
		return instance;
	}

	public void showSimpleDialog(Activity handler, String title, String content) {
		AlertDialog.Builder builder = new AlertDialog.Builder(handler);
		builder.setMessage(content);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(
				"De acuerdo!", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						alertDialog.dismiss();
						alertDialog = null;
					}
				});
		alertDialog = builder.create();
		alertDialog.show();
	}

	public void showLoading(Activity handler, String message) {
		showLoading(handler, "", message);
	}

	public void showLoading(Activity handler, String title, String message) {
		dialog = ProgressDialog.show(handler, title, message);
	}

	public void hideLoading() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

}

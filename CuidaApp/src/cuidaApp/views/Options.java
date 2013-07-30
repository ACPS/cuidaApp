package cuidaApp.views;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.cuidaapp.R;

import cuidaApp.common.TopBar;
import cuidaApp.controllers.MainController;
import cuidaApp.controllers.OptionsController;
import cuidaApp.controllers.PreferencesController;
import cuidaApp.util.AppGlobal;

public class Options extends FragmentActivity{

	private EditText txt_user_name;
	private EditText txt_user_last_name;
	private EditText txt_password_actual;
	private EditText txt_new_password;
	private EditText txt_repeat_new_password;

	private Button btn_send_dats;


	private OptionsController controller;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_options);

		TopBar.initializeTopBar(
				this,
				View.VISIBLE,
				View.GONE,
				View.VISIBLE,
				PreferencesController.getInstance().getPreferences(
						"actualActivity"), "options");
		
	

		initialize();
	}
	
	@SuppressLint("NewApi")
	private void initialize() {

		txt_user_name = (EditText) findViewById(R.id.txt_name);
		txt_user_last_name = (EditText) findViewById(R.id.txt_lastname);
		txt_password_actual = (EditText) findViewById(R.id.txt_passactual);
		txt_new_password = (EditText) findViewById(R.id.txt_newpass);
		txt_repeat_new_password = (EditText) findViewById(R.id.txt_repeat_password);

		
		btn_send_dats = (Button) findViewById(R.id.btn_update);
		controller = new OptionsController();

	
		btn_send_dats.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = txt_user_name.getText().toString();
				String last_name = txt_user_last_name.getText().toString();
				String password_actual = txt_password_actual.getText()
						.toString();
				String newpassword = txt_new_password.getText().toString();
				String repeatnewapassword = txt_repeat_new_password.getText()
						.toString();
				controller.updateUser(name, last_name, password_actual,
						newpassword, repeatnewapassword, Options.this);

			}
		});
		

		controller.showUser(txt_user_name, txt_user_last_name, this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			AppGlobal.getInstance().dispatcher.open(
					Options.this,
					PreferencesController.getInstance().getPreferences(
							"actualActivity"), true);
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClickBtnLogout(View v) {
		controller.logout(this);
	}

	public void onClickBtnRanking(View v) {
		AppGlobal.getInstance().dispatcher.open(this, "ranking", true);
	}

	public void onClickBtnHelp(View v) {
//		AppGlobal.getInstance().dispatcher.open(this, "help", true);
		final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://cuidapp.com/"));
		startActivity(intent);
	}
	
	public void onClickBtnTwitter(View v) {
//		AppGlobal.getInstance().dispatcher.open(this, "help", true);
		final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/cuidapp"));
		startActivity(intent);
	}
	
	
	public void BtnActivos(View v){
//		Images.init();
		MainController.getInstance().loadMyActives(this);
//        AppGlobal.getInstance().dispatcher.open(this, "activos", true);
	}
}

package cuidaApp.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cuidaapp.R;

import cuidaApp.common.TopBar;
import cuidaApp.controllers.CacheMemoryController;
import cuidaApp.controllers.RegisterController;

public class Registrar extends FragmentActivity{

	// private Requirements requirements;
		
		private EditText name;
		private EditText last_name;
		private EditText email;
		private EditText password ;
		private EditText repeatpassord;
		private RegisterController controller;
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		
		
		name = (EditText) findViewById(R.id.txt_name);
		last_name= (EditText) findViewById(R.id.txt_lastname);
		email = (EditText) findViewById(R.id.txt_passactual);
		password= (EditText) findViewById(R.id.txt_newpass);
		repeatpassord = (EditText) findViewById(R.id.txt_repeatnewpa);
		controller= new RegisterController();
		TopBar.initializeTopBar(this, View.VISIBLE, View.INVISIBLE, View.VISIBLE, "login","registrar");
		
		ImageView img = (ImageView)findViewById(R.id.image_fondo);
		CacheMemoryController.getInstance().loadBitmap(R.drawable.fondo, img);
	
	}
	
	public void onClicIngresar(View view) {

		String username = name
				.getText().toString();
		String userlastname = last_name
				.getText().toString();
		String emailtext = email.getText()
				.toString();
		String passwordtext = password
				.getText().toString();
		String repeatpassordtext = repeatpassord
				.getText().toString();

		controller.check_information(this, username,
				userlastname, emailtext, passwordtext, repeatpassordtext);

	}

	/*
	 * public void onClicLogin(View v) {
	 * AppGlobal.getInstance().dispatcher.open(this, "login", true); }
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			cuidaApp.util.AppGlobal.getInstance().dispatcher.open(Registrar.this, "login",
					true);
		}
		return super.onKeyDown(keyCode, event);
	}
}

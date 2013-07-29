/**
 * 
 */
package cuidaApp.views;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.cuidaapp.R;

import cuidaApp.common.TopBar;
import cuidaApp.controllers.ForgetPasswordController;
import cuidaApp.util.AppGlobal;

/**
 * @author LUSTER
 * 
 */
public class ForgetPassword extends FragmentActivity {

	//private Requirements requirements;
	private ForgetPasswordController controller;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		TopBar.initializeTopBar(this, View.VISIBLE, View.INVISIBLE, View.VISIBLE, "login","registrar");
		
		initialize();
		//ForgetPasswordController.getInstance().requirements=requirements;
	}

	public void onClicNext(View view) {
		String email = ((EditText) findViewById(R.id.text_email)).getText()
				.toString();
		controller.checkAndSendEmail(email, this);
		// colocar progess
	}
	
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	
	public void initialize(){
		controller = new ForgetPasswordController();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			AppGlobal.getInstance().dispatcher.open(ForgetPassword.this, "login",
					true);
		}
		return super.onKeyDown(keyCode, event);
	}
}
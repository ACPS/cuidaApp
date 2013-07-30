/**
 * 
 */
package cuidaApp.views;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.cuidaapp.R;

import cuidaApp.common.TopBar;
import cuidaApp.controllers.ChangePasswordController;
import cuidaApp.util.AppGlobal;

/**
 * @author LUSTER
 *
 */
public class ChangePassword extends FragmentActivity {
	
	private EditText code_one;
	private EditText code_two;
	private EditText code_three;
	private EditText code_four;
	private boolean codetwo=false;
	private boolean codethree=false;
	private boolean codefour=false;
	private ChangePasswordController controler;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);


		inizialite();
		
	}

	
	
	private void inizialite() {
		
		code_one = (EditText) findViewById(R.id.code_one);
		code_two = (EditText) findViewById(R.id.code_two);
		code_three = (EditText) findViewById(R.id.code_three);
		code_four = (EditText) findViewById(R.id.code_four);
		code_one.requestFocus();
		TopBar.initializeTopBar(this, View.VISIBLE, View.GONE, View.VISIBLE, "login","changepassword");
	
		controler = new ChangePasswordController();
		
	
		code_one.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// Log.v("TAG", "afterTextChanged");
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// Log.v("TAG", "beforeTextChanged");

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// Log.v("TAG", "onTextChanged");
				if (code_one.getText().toString().length() == 1) {
					code_two.requestFocus();
				}
			}
		});

		code_two.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// Log.v("TAG", "afterTextChanged");
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (code_two.getText().toString().length() == 1) {
					 code_three.requestFocus();
					 codetwo=true;
				}
			}
		});

		code_three.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// Log.v("TAG", "afterTextChanged");
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// Log.v("TAG", "beforeTextChanged");

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// Log.v("TAG", "onTextChanged");
				if (code_three.getText().toString().length() == 1) {
					code_four.requestFocus();
					codethree=true;
				}
			}
		});
		
		code_four.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// Log.v("TAG", "afterTextChanged");
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// Log.v("TAG", "beforeTextChanged");

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// Log.v("TAG", "onTextChanged");
				if (code_three.getText().toString().length() == 1) {
					codefour=true;
				}
			}
		});
	}

	
	public void onClicChange(View view){
		
		String code_one = ((EditText) findViewById(R.id.code_one)).getText().toString();
		String code_two = ((EditText) findViewById(R.id.code_two)).getText().toString();
		String code_three = ((EditText) findViewById(R.id.code_three)).getText().toString();
		String code_four = ((EditText) findViewById(R.id.code_four)).getText().toString();
		String code_confirmation = code_one + code_two + code_three + code_four;
		
		String password = ((EditText) findViewById(R.id.new_password)).getText().toString();
		String repeat_password = ((EditText) findViewById(R.id.repeat_password)).getText().toString();
		
		controler.checkAndSendData(
				code_confirmation, password, repeat_password, this);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == 67) {
			if (code_two.isFocused()) {
				if(!codetwo){
					code_one.requestFocus();
				}else{
					codetwo=false;
				}
			} else if (code_three.isFocused()) {
				if(!codethree){
					code_two.requestFocus();
				}else{
					codethree=false;
				}
			} else if (code_four.isFocused()) {
				if(!codefour){
					code_three.requestFocus();
				}else{
					codefour=false;
				}
				
			}
		}
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			AppGlobal.getInstance().dispatcher.open(ChangePassword.this, "forgetpassword",
					true);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	
}

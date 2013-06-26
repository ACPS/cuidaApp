package cuidaApp.views;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

import cuidaApp.common.TopBar;
import cuidaApp.controllers.EmailConfirmationController;
import cuidaApp.util.AppGlobal;

public class EmailConfirmation extends FragmentActivity {

	private EditText code_one;
	private EditText code_two;
	private EditText code_three;
	private EditText code_four;
	private String code;
	EmailConfirmationController controller;
	
	private boolean codetwo=false;
	private boolean codethree=false;
	private boolean codefour=false;
	private final String TAG = "Activity EmailConfirmation";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_email_confirmation);
		inizialite();
		
	}
	
	public void sendData(View v) {
		code = code_one.getText().toString() + code_two.getText().toString()
				+ code_three.getText().toString()
				+ code_four.getText().toString();
		Trace.i("EmailConfirmation", code);

		controller.check_code(this, code);
	}
	
	public void codeProblems(View v) {
		AppGlobal.getInstance().dispatcher.open(this, "codeproblems", true);
	}
	
	public void inizialite(){
		code_one = (EditText) findViewById(R.id.code_one);
		code_two = (EditText) findViewById(R.id.code_two);
		code_three = (EditText) findViewById(R.id.code_three);
		code_four = (EditText) findViewById(R.id.code_four);
		code_one.requestFocus();
		TopBar.initializeTopBar(this, View.GONE, View.VISIBLE, View.VISIBLE, "login","emailconfirmation");
		
		controller = new EmailConfirmationController();
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
			AppGlobal.getInstance().dispatcher.open(EmailConfirmation.this,
					"login", true);
		}
		return super.onKeyDown(keyCode, event);
	}

}

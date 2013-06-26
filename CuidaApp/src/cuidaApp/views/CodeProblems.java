package cuidaApp.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.cuidaapp.R;

import cuidaApp.common.TopBar;
import cuidaApp.controllers.CodeProblemsController;

public class CodeProblems extends FragmentActivity {

	private EditText emailView;
	private CodeProblemsController controller;
	private final String TAG = "Activity CodeProblems";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_code_problems);
		initialize();
	}

	

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	public void onResume()
	{
	    super.onResume();
	}
	
	public void onPause()
	{
	    super.onPause();
	}

	private void initialize() {
		emailView = (EditText) findViewById(R.id.text_email);
		TopBar.initializeTopBar(this, View.VISIBLE, View.GONE, View.VISIBLE,
				"emailconfirmation", "codeproblems");
		controller = new CodeProblemsController();
		
	}

	public void sendCode(View v) {
		controller.sendCode(this,
				emailView.getText().toString());
	}
}

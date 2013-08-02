package cuidaApp.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cuidaapp.R;

import cuidaApp.common.TopBar;
import cuidaApp.controllers.CacheMemoryController;
import cuidaApp.controllers.CodeProblemsController;

public class CodeProblems extends FragmentActivity {

	private EditText emailView;
	private CodeProblemsController controller;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
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
		
		ImageView img = (ImageView)findViewById(R.id.image_fondo);
		CacheMemoryController.getInstance().loadBitmap(R.drawable.fondo, img);
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

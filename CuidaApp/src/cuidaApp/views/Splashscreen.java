package cuidaApp.views;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.cuidaapp.R;

import cuidaApp.controllers.PreferencesController;
import cuidaApp.util.AppGlobal;



public class Splashscreen extends Activity{

	private boolean mIsBackButtonPressed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splashscreen);
		
		Handler handler = new Handler();
		handler.postDelayed(getRunnableStartApp(), 1000);
	}
	
	private Runnable getRunnableStartApp() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				finish();
				if (!mIsBackButtonPressed) {
					if(PreferencesController.getInstance().isPrefences("active")){
						AppGlobal.getInstance().dispatcher.open(Splashscreen.this, "main",true);
					}else{
						AppGlobal.getInstance().dispatcher.open(Splashscreen.this, send(),true);
					}
				}
			}
		};		return runnable;
	}
	
	public String send(){
		
		//	Trace.i(TAG, PreferencesController.getInstance().getPreferences("token"));
			if((PreferencesController.getInstance().isPrefences("token"))&&(!PreferencesController.getInstance().isPrefences("active"))){
				return "emailconfirmation";
			}else{
				return "login";
			}
			
	}
}

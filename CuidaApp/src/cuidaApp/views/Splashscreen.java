package cuidaApp.views;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
				
				if (!mIsBackButtonPressed) {
					if(PreferencesController.getInstance().isPrefences("active")){
						if(!PreferencesController.getInstance().isPrefences("first")){
							Intent intent = new Intent(Splashscreen.this, FirstHelp.class);
					        startActivity(intent);
					    	finish();
						}else{
							Intent intent = new Intent(Splashscreen.this, ActvieGPS.class);
					        startActivity(intent);
					    	finish();
						}
					}else{
						
						send();
					}
				}
			}
		};		return runnable;
	}
	
	public void send(){
			//	Trace.i(TAG, PreferencesController.getInstance().getPreferences("token"));
			if((PreferencesController.getInstance().isPrefences("token"))&&(!PreferencesController.getInstance().isPrefences("active"))){
				
				Intent intent = new Intent(Splashscreen.this, EmailConfirmation.class);
		        startActivity(intent);
		    	finish();
			}else{
				if(!PreferencesController.getInstance().isPrefences("first")){
				
					Intent intent = new Intent(Splashscreen.this, FirstHelp.class);
			        startActivity(intent);
			    	finish();
				}else{
				
					Intent intent = new Intent(Splashscreen.this, ActvieGPS.class);
					startActivity(intent);
					finish();
			    	
				}
				
			}
			
	}
}

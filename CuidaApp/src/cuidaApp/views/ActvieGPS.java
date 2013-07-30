package cuidaApp.views;

import android.app.Activity;
import android.bitmapfun.provider.Images;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.Window;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.common.ListenerGPS;
import cuidaApp.controllers.MainController;
import cuidaApp.util.AppGlobal;

public class ActvieGPS extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_warning_gps);
	}

	public void ActiveGPS(View v){
		startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(CommonGlobals.getGPSStatus(this)){
			
			
			if(Images.imageThumbUrls.size()==0){
				ListenerGPS.getInstance().stopListener();
				ListenerGPS.getInstance().obtenerUbicacion(this);
				CommonGlobals.showProgess(this);
				Handler handler = new Handler();
				handler.postDelayed(getRunnableStartApp(), 3000);
			}else{
				   //CommonGlobals.hideProgess();
				   ListenerGPS.getInstance().stopListener();
				   AppGlobal.getInstance().dispatcher.open(ActvieGPS.this, "category", true);
				   
			}
			
		}
	}
	
	private Runnable getRunnableStartApp() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				
				while(true){
				if((ListenerGPS.getInstance().latitud!=0)&&(ListenerGPS.getInstance().longitud!=0)){
							CommonGlobals.hideProgess();
							ListenerGPS.getInstance().stopListener();
							MainController.getInstance().loadCategory(ActvieGPS.this);
							break;
						}
				}
			}
				
			
		};		return runnable;
	}
}

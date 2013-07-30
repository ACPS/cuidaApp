package cuidaApp.views;

import android.app.Activity;
import android.bitmapfun.provider.Images;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.controllers.MainController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.util.AppGlobal;

public class ActvieGPS extends Activity  implements LocationListener{
	
	private LocationManager locationManager;
	
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
				CommonGlobals.showProgess(this);
				locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 0, 0, this);
			}else{
				AppGlobal.getInstance().dispatcher.open(ActvieGPS.this, "category", true); 
			}
			
		}
	}
	
	

	@Override
	public void onLocationChanged(Location location) {
		MainController.getInstance().loadCategory(ActvieGPS.this,location.getLatitude(),location.getLongitude());
		locationManager.removeUpdates(this);
		ManagerController.getInstance().setLatitude(location.getLatitude());
		ManagerController.getInstance().setLongitude(location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(locationManager!=null){
			locationManager.removeUpdates(this);
		}
	}
}

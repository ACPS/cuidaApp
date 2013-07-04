package cuidaApp.views;




import android.Manifest.permission;
import android.app.Activity;
import android.app.AlertDialog;
import android.bitmapfun.provider.Images;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;
import com.nerdcore.utils.CheckPermissionsUtils;

import cuidaApp.common.ListenerGPS;
import cuidaApp.common.TopBar;
import cuidaApp.controllers.MainController;
import cuidaApp.util.AppGlobal;

public class MainActivity extends FragmentActivity {

	private MainController controller=new MainController();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		TopBar.initializeTopBar(this, View.INVISIBLE, View.VISIBLE, View.VISIBLE,
				"", "main");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void BtnTakePicture(View v){
//		  AppGlobal.getInstance().dispatcher.open(this, "take", true);
		if(Images.imageUrls.size()==0){
			//si el gps esta encendido
			if(getGPSStatus()){
				if((ListenerGPS.getInstance().latitud!=0)&&(ListenerGPS.getInstance().longitud!=0)){
					ListenerGPS.getInstance().stopListener();
					controller.loadCategory(this);
				}else{
					ubicationNoFound();
				}
			}else{
				activeGPS();
			}

		}else{
			AppGlobal.getInstance().dispatcher.open(this, "category", true);
		}
		
	}
	public void BtnActivos(View v){
//		Images.init();
        AppGlobal.getInstance().dispatcher.open(this, "activos", true);
	}
	
	public boolean getGPSStatus() {

		if (CheckPermissionsUtils.is(permission.ACCESS_FINE_LOCATION)) {

			LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				return false;
			} else {
				return true;
			}

		} else {
			return false;
		}
	}
	
	private void activeGPS(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_no_gps_content)
				.setTitle(R.string.dialog_no_gps_title)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.dialog_no_gps_acept,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton(R.string.dialog_no_gps_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								dialog.cancel();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void ubicationNoFound(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_no_gps_content)
				.setTitle(R.string.dialog_no_gps_title)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.dialog_no_gps_acept,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}
}

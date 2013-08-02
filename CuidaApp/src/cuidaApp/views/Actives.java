package cuidaApp.views;

import java.text.DecimalFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.example.cuidaapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import cuidaApp.controllers.MainController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.models.Activo;
import cuidaApp.util.AppGlobal;

public class Actives extends Activity implements 
		OnClickListener {

	private GoogleMap mMap;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.activity_actives);

	
		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.report_map)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);




	
		addMarkers();

	}

	private void addMarkers() {
		
		LatLng latlon = null;

		List<Activo> actives = MainController.getInstance().getActivos();
		for(Activo act :actives){
			latlon = new LatLng(act.getLat(), act.getLon());
			if(act.getPosicion_icon()!=-1){
				if(MainController.getInstance().getIcons().get(act.getPosicion_icon()).getImage()!=null){
					mMap.addMarker(new MarkerOptions().position(latlon).icon(
							BitmapDescriptorFactory.fromBitmap(MainController.getInstance().getIcons().get(act.getPosicion_icon()).getImage())));
				}else{
					mMap.addMarker(new MarkerOptions().position(latlon));
				}
			}else{
				mMap.addMarker(new MarkerOptions().position(latlon));
			}
		}
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {

				
				Activo act = getActive(marker.getPosition());

				if (act != null) {
					ManagerController.getInstance().setSelectedActivo(act);
					AppGlobal.getInstance().dispatcher.open(Actives.this,
							"detail", true);
				} else {
					
				}

				return false;
			}
		});
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlon, 18), 200,
				new CancelableCallback() {

					@Override
					public void onFinish() {
					}

					@Override
					public void onCancel() {
					}
				});
	}

	public Activo getActive(LatLng posicion) {

		DecimalFormat decimal = new DecimalFormat("0.00000");
		List<Activo> activos = MainController.getInstance().getActivos();
		for (Activo act : activos) {

			// Log.i("Posición:",decimal.format(act.getLat())+"-"+pos);
			if (decimal.format(act.getLat()).equals(
					decimal.format(posicion.latitude))) {

				if (decimal.format(act.getLon()).equals(
						decimal.format(posicion.longitude))) {
					return act;
				}
			}

		}
		return null;
	}

	

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			AppGlobal.getInstance().dispatcher.open(Actives.this, "options", true);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}

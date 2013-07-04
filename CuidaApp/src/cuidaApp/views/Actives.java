package cuidaApp.views;

import java.text.DecimalFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import com.example.cuidaapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import cuidaApp.controllers.ConfirmController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.models.Activo;
import cuidaApp.util.AppGlobal;

public class Actives extends Activity implements LocationListener,
		OnClickListener {

	private GoogleMap mMap;
	private LocationManager locationManager;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_actives);

	
		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.report_map)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);




		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);


		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, this);

	
//		addMarkers();

	}

	private void addMarkers() {
		
		LatLng latlon = null;
		for (Activo act : ConfirmController.getInstance().getActivos()) {
			latlon = new LatLng(act.getLat(), act.getLon());
			Bitmap icon = ManagerController.getInstance().getSelectedCategory()
					.getNormal();

			if (act.getEstado().equalsIgnoreCase("normal")) {
				icon = ManagerController.getInstance().getSelectedCategory()
						.getNormal();
			}
			if (act.getEstado().equalsIgnoreCase("reported")) {
				icon = ManagerController.getInstance().getSelectedCategory()
						.getReported();
			}
			if (act.getEstado().equalsIgnoreCase("attended")) {
				icon = ManagerController.getInstance().getSelectedCategory()
						.getAttended();
			}
			if (act.getEstado().equalsIgnoreCase("repaired")) {
				icon = ManagerController.getInstance().getSelectedCategory()
						.getRepaired();
			}

			if (icon != null) {
				mMap.addMarker(new MarkerOptions().position(latlon).icon(
						BitmapDescriptorFactory.fromBitmap(icon)));
			} else {
				mMap.addMarker(new MarkerOptions().position(latlon));
			}

		}

		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {

				Log.i("REPORT", marker.getPosition().toString());
				Activo act = getActive(marker.getPosition());

				if (act != null) {
					ManagerController.getInstance().setSelectedActivo(act);
					AppGlobal.getInstance().dispatcher.open(Actives.this,
							"confirm", true);
				} else {
					Log.i("REPORT", "null");
				}

				return false;
			}
		});
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlon, 16), 200,
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

		DecimalFormat decimal = new DecimalFormat("0.000000");
		List<Activo> activos = ConfirmController.getInstance().getActivos();
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
	public void onLocationChanged(Location location) {
		// LatLng latlon = new LatLng(location.getLatitude(),
		// location.getLongitude());

		// if (marker == null) {
		//
		//
		// marker = mMap.addMarker(new MarkerOptions().position(latlon).title(
		// "Posici—n actual"));
		//
		// marker.setDraggable(true);
		//
		//
		// } else {
		// marker.setPosition(latlon);
		// }
		// mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlon, 16),
		// 200,
		// new CancelableCallback() {
		//
		// @Override
		// public void onFinish() {
		// }
		//
		// @Override
		// public void onCancel() {
		// }
		// });
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			AppGlobal.getInstance().dispatcher.open(Actives.this, "main", true);
		}
		return super.onKeyDown(keyCode, event);
	}

}

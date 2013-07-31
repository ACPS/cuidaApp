package cuidaApp.views;

import java.text.DecimalFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

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

import cuidaApp.common.Exit;
import cuidaApp.controllers.ConfirmController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.controllers.PreferencesController;
import cuidaApp.models.Activo;
import cuidaApp.util.AppGlobal;
public class Report extends Activity implements LocationListener,
		OnClickListener {

	private GoogleMap mMap;
	private LocationManager locationManager;
	
	private Marker marker;
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_report);
	
		
		// === Buttons
		
		
		// === Map
		Button btn_options= (Button)findViewById(R.id.top_bar_btn_options);
		if(!PreferencesController.getInstance().isPrefences("active")){
			btn_options.setVisibility(View.INVISIBLE);
		}
	

		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.report_map)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// === Location

		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Register the listener with the Location Manager to receive location
		// updates
		
		

//        ImageCacheParams cacheParams = new ImageCacheParams(this, IMAGE_CACHE_DIR);
//
//        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
//
//        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
//        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
//        mImageFetcher = new ImageFetcher(this, mImageThumbSize);
//        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
//        mImageFetcher.addImageCache((FragmentManager)this, cacheParams);
        
		
		if((ManagerController.getInstance().getLatitude()!=0)&&(ManagerController.getInstance().getLatitude()!=0)){
			LatLng latlon = new LatLng(ManagerController.getInstance().getLatitude(), ManagerController.getInstance().getLatitude());
			if (marker == null) {
				Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_map);
				marker = mMap.addMarker(new MarkerOptions().position(latlon).title(
						"Posición actual").icon(BitmapDescriptorFactory.fromBitmap(icon)));
				
				marker.setDraggable(true);
				
			
			} else {
				marker.setPosition(latlon);
			}
		}
		
		addMarkers();
		
		
		
	}

	

	private void addMarkers() {
		// TODO Auto-generated method stub
//		LatLng latlon = new LatLng(10.985554113965193,
//				-74.77736481258788);
//		if (marker == null) {
//			
//			ImageView image=  new RecyclingImageView(this);
//			mImageFetcher.loadImage("http://icons.iconarchive.com/icons/icons-land/vista-map-markers/16/Map-Marker-Marker-Outside-Azure-icon.png", image);
////			
////			marker = mMap.addMarker(new MarkerOptions().position(latlon).title(
////					"Posici—n actual").icon(BitmapDescriptorFactory.fromBitmap(image.getDrawingCache())));
//			
////			marker.setDraggable(true);
//		
//		} else {
//			marker.setPosition(latlon);
//		}
		LatLng latlon =null;
		for(Activo act: ConfirmController.getInstance().getActivos()){
			latlon = new LatLng(act.getLat(),
					act.getLon());
			Bitmap icon=ManagerController.getInstance().getSelectedCategory().getNormal();
			
			if(act.getEstado().equalsIgnoreCase("normal")){
				icon=ManagerController.getInstance().getSelectedCategory().getNormal();
			}
			if(act.getEstado().equalsIgnoreCase("reported")){
				icon=ManagerController.getInstance().getSelectedCategory().getReported();
			}
			if(act.getEstado().equalsIgnoreCase("attended")){
				icon=ManagerController.getInstance().getSelectedCategory().getAttended();
			}
			if(act.getEstado().equalsIgnoreCase("repaired")){
				icon=ManagerController.getInstance().getSelectedCategory().getRepaired();
			}
			
			if(icon!=null){
				mMap.addMarker(new MarkerOptions().position(latlon).icon(BitmapDescriptorFactory.fromBitmap(icon)));
			}else{
				mMap.addMarker(new MarkerOptions().position(latlon));
			}
			
			
		}
		
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				
				Log.i("REPORT",marker.getPosition().toString());
				Activo act = getActive(marker.getPosition());

				if(act!=null){
					ManagerController.getInstance().setSelectedActivo(act);
					if(PreferencesController.getInstance().isPrefences("active")){
					   AppGlobal.getInstance().dispatcher.open(Report.this, "take",true);
					}else{
						startSession();
					}
				}else{
					Log.i("REPORT","null");
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 0, 0, this);

		super.onResume();
	}
	
	public Activo getActive(LatLng posicion){
		
		DecimalFormat decimal= new DecimalFormat("0.00000");
		List<Activo> activos= ConfirmController.getInstance().getActivos();
		for(Activo act:  activos){
			
			//Log.i("Posición:",decimal.format(act.getLat())+"-"+pos);
			if(decimal.format(act.getLat()).equals(decimal.format(posicion.latitude))){
				
				if(decimal.format(act.getLon()).equals(decimal.format(posicion.longitude))){
					return act;
				}
			}
			
		}
		return null;
	}


	@Override
	public void onLocationChanged(Location location) {
		LatLng latlon = new LatLng(location.getLatitude(),
				location.getLongitude());
		ManagerController.getInstance().setLatitude(location.getLatitude());
		ManagerController.getInstance().setLongitude(location.getLongitude());
		
		if (marker == null) {
			
			Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_map);
			marker = mMap.addMarker(new MarkerOptions().position(latlon).title(
					"Posición actual").icon(BitmapDescriptorFactory.fromBitmap(icon)));
			
			marker.setDraggable(true);
			
		
		} else {
			marker.setPosition(latlon);
		}
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
			AppGlobal.getInstance().dispatcher.open(
					Report.this,
					"category", true);
		}
		return super.onKeyDown(keyCode, event);
	}

	public void startSession(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Para continuar y enviar su reporte debe iniciar sesión")
				.setTitle(R.string.information_title)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.error_acept,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								AppGlobal.getInstance().dispatcher.open(Report.this, "login",true);
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	 public void onclicBtnBack(View v){
		AppGlobal.getInstance().dispatcher.open(this, "category", true);
	}
	    
    public void onClickBtnClose(View v){
    	Exit.buildAlertExit(this);
    }
	   
    public void onClickBtnOptions(View v){
    	PreferencesController.getInstance().addPreferences("actualActivity", "report");
		AppGlobal.getInstance().dispatcher.open(this, "options", true);
    }
    
    @Override
	protected void onStop() {

		
		
		// mPreview.stop();
		
		locationManager.removeUpdates(this);
	
		// Requirements.getInstance().stop();
		super.onStop();
	}
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
    	locationManager.removeUpdates(this);
		super.onPause();
	}
}

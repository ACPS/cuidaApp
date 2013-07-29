package cuidaApp.views;


import java.util.List;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

import cuidaApp.common.CommonGlobals;
import cuidaApp.common.ListenerGPS;
import cuidaApp.common.TopBar;
import cuidaApp.controllers.ConfirmController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.controllers.PreferencesController;
import cuidaApp.controllers.SurfaceController;
import cuidaApp.util.AppGlobal;

public class SendReport extends FragmentActivity implements SensorEventListener {

	private SurfaceController mPreview;
	private FrameLayout camera_surface;
	private static final String TAG = "Activity SendReport";
	private ImageView image_preview;
	private LinearLayout container_image_preview;
	private float curX = 0, curY = 0, curZ = 0;
	private Button btn_take_a_pic;
	private Button btn_cancel;
	private Button btn_done;
	private SensorManager sensormanager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_send);
		Log.i(TAG,"ONCREATE");
		camera_surface = (FrameLayout) findViewById(R.id.camera_surface);
		
		image_preview = (ImageView) findViewById(R.id.image_preview);
		container_image_preview = (LinearLayout) findViewById(R.id.container_image_preview);
		btn_take_a_pic= (Button) findViewById(R.id.btn_pic);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_done = (Button) findViewById(R.id.btn_done);
		
		
		TopBar.initializeTopBar(this, View.VISIBLE, View.VISIBLE, View.VISIBLE,
				"report", "take");
		
		// Create our Preview view and set it as the content of our activity.
		mPreview = new SurfaceController(this);
		camera_surface.addView(mPreview);
		Trace.i(TAG, "onCreate");
	}
	@Override
	protected void onResume() {
		if (ManagerController.getInstance().getImageTemp() != null) {
			camera_surface.setVisibility(View.INVISIBLE);
			image_preview.setImageBitmap(ManagerController.getInstance()
					.getImageTemp());
			showOptions(View.VISIBLE);
			btn_take_a_pic.setEnabled(false);
			Trace.i(TAG, "!=NULL");
			stopSensorManager();

		}else{
			camera_surface.setVisibility(View.VISIBLE);
			startSensorManager();
		}


		super.onResume();
	}
	
	public void takePicture(View v) {

		//stopSensorManager();
		Trace.i(TAG, "takePicture");
		camera_surface.setVisibility(View.GONE);
		CommonGlobals.showProgess(this);
		mPreview.takeAPicAction(this.camera_surface, this.image_preview,
				container_image_preview, curX, curY, curZ);

		btn_take_a_pic.setEnabled(false);

		this.image_preview.setVisibility(View.GONE);
		
		Handler handler = new Handler();
		handler.postDelayed(showOptions(), 1000);
		
		// btn_take_a_pic.setVisibility(View.GONE);
		Log.i("SendReport",ManagerController.getInstance().getImageTemp()+"");
	}
	
	
	public void BtnDoneClic(View v){
		
		AppGlobal.getInstance().dispatcher.open(this, "confirm", true);
		
	}
	
	public void BtnCancelClic(View v){
		if (ManagerController.getInstance().getImageTemp() != null)
			ManagerController.getInstance().getImageTemp().recycle();
		ManagerController.getInstance().setImageTemp(null);
		mPreview.resume();
		camera_surface.setVisibility(View.VISIBLE);
		btn_take_a_pic.setEnabled(true);
		
		showOptions(View.GONE);
		
		ManagerController.getInstance().setPreviewing(true);
		//ListenerGPS.getInstance().obtenerUbicacion();
		btn_take_a_pic.setVisibility(View.VISIBLE);
		startSensorManager();
		//startSensorManager();
		//Requirements.getInstance().setListenGps(true);
		//Requirements.getInstance().getBtn_no_gps().setVisibility(View.VISIBLE);
	}
	private Runnable showOptions() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				showOptions(View.VISIBLE);
			}
		};

		return runnable;
	}
	
	public void showOptions(int option) {
		btn_cancel.setVisibility(option);
		btn_done.setVisibility(option);
		container_image_preview.setVisibility(option);
		Log.i("show","aaaa");
	}
	
	public void Confirm(View v){

 		Intent intent = new Intent(SendReport.this, Confirm.class);
        startActivity(intent);
        finish();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		curX = event.values[0];
		curY = event.values[1];
		curZ = event.values[2];

		int value_y = (int) curY;
		// Trace.i(TAG, "y: "+value_y + "x: " + curX+ " z"+curZ);
		if (value_y == 0) {
			if ((int) curX >= 0) {
//				btn_take_a_pic
//						.setBackgroundResource(alcaldiadebarranquilla.movilidad.android.R.drawable.bg_btn_camera_vertical);

			} else {
//				btn_take_a_pic
//						.setBackgroundResource(alcaldiadebarranquilla.movilidad.android.R.drawable.bg_btn_camera_vertical_negative);

			}
		}

		if (value_y == 9) {
//			btn_take_a_pic
//					.setBackgroundResource(alcaldiadebarranquilla.movilidad.android.R.drawable.bg_btn_camera);
		} else if (value_y == -9) {
//			btn_take_a_pic
//					.setBackgroundResource(alcaldiadebarranquilla.movilidad.android.R.drawable.bg_btn_camera_down);
		}
	}
	
	public void startSensorManager() {
		sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
		List<Sensor> sensors = sensormanager
				.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sensormanager.registerListener(this, sensors.get(0),
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	public void stopSensorManager() {
		sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensormanager.unregisterListener(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			AppGlobal.getInstance().dispatcher.open(
						SendReport.this,"report", true);
		}
		return super.onKeyDown(keyCode, event);
	}
}

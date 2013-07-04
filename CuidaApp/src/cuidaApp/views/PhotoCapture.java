package cuidaApp.views;

import java.util.List;

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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

import cuidaApp.common.CommonGlobals;
import cuidaApp.common.Exit;
import cuidaApp.common.ListenerGPS;
import cuidaApp.common.TopBar;
import cuidaApp.controllers.ConfirmController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.controllers.SurfaceController;
import cuidaApp.util.AppGlobal;

public class PhotoCapture extends FragmentActivity implements
		SensorEventListener {

	private static final String TAG = "Activity PhotoCapture";
	

	private SurfaceController mPreview;
	private Button btn_take_a_pic;
	private Button btn_cancel;
	private Button btn_done;
	private FrameLayout camera_surface;
	private LinearLayout container_image_preview;

	protected static final int ON_IMAGE_ERROR = 1;
	protected static final int ON_IMAGE_OK = 2;
	private ImageView image_preview;
	private float curX = 0, curY = 0, curZ = 0;
	private SensorManager sensormanager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_send);
		// Trace.savedInstanceState.get("rotation");
		
		initialize();

		
			Log.i(TAG,"ONCREATE");
			
		mPreview = new SurfaceController(this);
		camera_surface.addView(mPreview);
		Trace.i(TAG, "onCreate");
	}

	@Override
	protected void onResume() {

		super.onResume();
		
		Trace.i(TAG, "onResume");
		// mPreview.open();
		// mPreview.resume();

		if (ManagerController.getInstance().getImageTemp() != null) {
			camera_surface.setVisibility(View.INVISIBLE);
			image_preview.setImageBitmap(ManagerController.getInstance()
					.getImageTemp());
			show_hide_categories(View.VISIBLE);
			btn_take_a_pic.setEnabled(false);
			Trace.i(TAG, "!=NULL");
			stopSensorManager();

		}else{
			ListenerGPS.getInstance().obtenerUbicacion(this);
			startSensorManager();
		}


//		Requirements.getInstance(PhotoCapture.this, true, true, true,
//				btn_take_a_pic, btn_done, btn_cancel, close_help);

		
		

	}

	@Override
	protected void onStop() {

		Trace.i(TAG, "onStop");
		stopSensorManager();
		// mPreview.stop();
		ListenerGPS.getInstance().stopListener();
		// Requirements.getInstance().stop();
		super.onStop();
	}



	@Override
	protected void onDestroy() {
		Trace.i(TAG, "onDestroy");
		super.onDestroy();
	}

	public void takePicture(View v) {

		stopSensorManager();
		Trace.i(TAG, "takePicture");
		camera_surface.setVisibility(View.GONE);
		CommonGlobals.showProgess(this);
		mPreview.takeAPicAction(this.camera_surface, this.image_preview,
				container_image_preview, curX, curY, curZ);

		btn_take_a_pic.setEnabled(false);



		this.image_preview.setVisibility(View.GONE);
		Handler handler = new Handler();
		handler.postDelayed(showCategories(), 1000);
		btn_take_a_pic
				.setBackgroundResource(R.drawable.boton_camara);

		
		// btn_take_a_pic.setVisibility(View.GONE);
	}

	

	public void initialize() {

		// get components of the view
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_done = (Button) findViewById(R.id.btn_done);
		btn_take_a_pic = (Button) findViewById(R.id.btn_pic);

	
		// Requirements.getInstance().restart();

		

		
		image_preview = (ImageView) findViewById(R.id.image_preview);
		camera_surface = (FrameLayout) findViewById(R.id.camera_surface);

		
		
		TopBar.initializeTopBar(this, View.VISIBLE, View.VISIBLE, View.VISIBLE,
				"category", "take");
		container_image_preview = (LinearLayout) findViewById(R.id.container_image_preview);

		

		

		btn_done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// show_hide_categories(View.GONE);
				
					mPreview.releaseCamara();
					ConfirmController.getInstance().loadActives(PhotoCapture.this);
			}
		});

	}

	

	public void show_hide_categories(int option) {
		btn_cancel.setVisibility(option);
		btn_done.setVisibility(option);
		container_image_preview.setVisibility(option);
	}

	private Runnable showCategories() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				show_hide_categories(View.VISIBLE);
			}
		};

		return runnable;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			AppGlobal.getInstance().dispatcher.open(
						PhotoCapture.this,"category", true);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		curX = event.values[0];
		curY = event.values[1];
		curZ = event.values[2];

		
		// Trace.i(TAG, "y: "+value_y + "x: " + curX+ " z"+curZ);
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

	
	public void BtnCancelClic(View v){
		if (ManagerController.getInstance().getImageTemp() != null)
			ManagerController.getInstance().getImageTemp().recycle();
		ManagerController.getInstance().setImageTemp(null);
		mPreview.resume();
		camera_surface.setVisibility(View.VISIBLE);
		btn_take_a_pic.setEnabled(true);
		show_hide_categories(View.GONE);
		
		
		ListenerGPS.getInstance().obtenerUbicacion(this);
		btn_take_a_pic.setVisibility(View.VISIBLE);
		startSensorManager();
	}
}
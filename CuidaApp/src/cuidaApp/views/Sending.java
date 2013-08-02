package cuidaApp.views;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.controllers.CacheMemoryController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.controllers.SendingController;

public class Sending extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sending_report);
	
		ImageView img = (ImageView)findViewById(R.id.image_fondo);
		CacheMemoryController.getInstance().loadBitmap(R.drawable.fondo, img);
		
		img = (ImageView)findViewById(R.id.img_sending);
		CacheMemoryController.getInstance().loadBitmap(R.drawable.img_enviando, img);
		
		if (!SendingController.getInstance().getsending()) {
			ManagerController.getInstance().addImage();
			SendingController.getInstance().createEntity();
			SendingController.getInstance().sendData(this);
		}
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			CommonGlobals.show_alert(this, "Espere mientras su reporte es enviado");
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

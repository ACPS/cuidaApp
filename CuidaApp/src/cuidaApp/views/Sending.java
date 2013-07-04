package cuidaApp.views;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.controllers.ManagerController;
import cuidaApp.controllers.SendingController;

public class Sending extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sending_report);
		
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

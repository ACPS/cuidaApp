package cuidaApp.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuidaapp.R;

import cuidaApp.common.TopBar;
import cuidaApp.controllers.ManagerController;
import cuidaApp.util.AppGlobal;

public class DetailActive extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
		TopBar.initializeTopBar(this, View.VISIBLE, View.VISIBLE, View.VISIBLE,
				"activos", "detail");
		ImageView preview = (ImageView) findViewById(R.id.image_preview1);
		preview.setImageBitmap(ManagerController.getInstance().getImageTemp());
		
		ImageView camera = (ImageView) findViewById(R.id.img_camera);
		camera.bringToFront();
		
		TextView tittle_category = (TextView) findViewById(R.id.tittle_category);
		TextView address_active = (TextView) findViewById(R.id.address_active);
		if(ManagerController.getInstance().getSelectedActivo()!=null){
			tittle_category.setText(ManagerController.getInstance().getSelectedActivo().getName_category());
			address_active.setText(ManagerController.getInstance().getSelectedActivo().getAdress());
		}
		
		
		
	}
	

	public void DeleteActive(View v){
		
	}
	
	public void BtnBack(View v){
		AppGlobal.getInstance().dispatcher.open(this, "activos", true);
	}
}
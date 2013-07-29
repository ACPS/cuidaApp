package cuidaApp.views;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.common.TopBar;
import cuidaApp.controllers.ManagerController;
import cuidaApp.util.AppGlobal;

public class Confirm extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_confirm);
		TopBar.initializeTopBar(this, View.VISIBLE, View.VISIBLE, View.VISIBLE,
				"take", "take");
		ImageView preview = (ImageView) findViewById(R.id.image_preview1);
		preview.setImageBitmap(ManagerController.getInstance().getImageTemp());
		
		ImageView camera = (ImageView) findViewById(R.id.img_camera);
		camera.bringToFront();
		
		TextView tittle_category = (TextView) findViewById(R.id.tittle_category);
		if(ManagerController.getInstance().getSelectedCategory().getName()!=null){
			tittle_category.setText(ManagerController.getInstance().getSelectedCategory().getName());
		}else{
			tittle_category.setText("");
		}
		TextView address_active = (TextView) findViewById(R.id.address_active);
		if(ManagerController.getInstance().getSelectedActivo().getAdress()!=null){
			address_active.setText(ManagerController.getInstance().getSelectedActivo().getAdress());
		}else{
			address_active.setText("");
		}
		
	}
	

	public void Sending(View v){
		EditText description = (EditText)findViewById(R.id.description);
		if(description.getText().toString().length()==0){
			CommonGlobals.show_alert(this, getString(R.string.activity_confirm_error_description_required));
		}else if(description.getText().toString().length()<=4){
			CommonGlobals.show_alert(this, getString(R.string.activity_confirm_error_description_invalid));
		}else{
			ManagerController.getInstance().setDescription(description.getText().toString());
//			ConfirmController.getInstance().loadActives(this);
			AppGlobal.getInstance().dispatcher.open(this, "sending", true);
		}
	}
	
	public void Cancel(View v){
		AppGlobal.getInstance().dispatcher.open(this, "report", true);
	}
}

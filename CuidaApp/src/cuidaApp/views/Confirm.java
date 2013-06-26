package cuidaApp.views;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.common.TopBar;
import cuidaApp.controllers.ConfirmController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.util.AppGlobal;

public class Confirm extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_confirm);
		TopBar.initializeTopBar(this, View.VISIBLE, View.VISIBLE, View.VISIBLE,
				null, "take");
		ImageView preview = (ImageView) findViewById(R.id.image_preview1);
		preview.setImageBitmap(ManagerController.getInstance().getImageTemp());
	}
	

	public void Sending(View v){
		EditText description = (EditText)findViewById(R.id.description);
		if(description.getText().toString().length()==0){
			CommonGlobals.show_alert(this, getString(R.string.activity_confirm_error_description_required));
		}else if(description.getText().toString().length()<=4){
			CommonGlobals.show_alert(this, getString(R.string.activity_confirm_error_description_invalid));
		}else{
			ManagerController.getInstance().setAddress(description.getText().toString());
//			ConfirmController.getInstance().loadActives(this);
			AppGlobal.getInstance().dispatcher.open(this, "sending", true);
		}
	}
	
	public void Cancel(View v){
		AppGlobal.getInstance().dispatcher.open(this, "take", true);
	}
}

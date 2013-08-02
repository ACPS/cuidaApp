package cuidaApp.views;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cuidaapp.R;

import cuidaApp.controllers.CacheMemoryController;
import cuidaApp.controllers.LoginController;

public class Login extends Activity {
	
	private LoginController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		ImageView img = (ImageView)findViewById(R.id.image_fondo);
		CacheMemoryController.getInstance().loadBitmap(R.drawable.fondo, img);
		controller = new LoginController();
	}
 
 	public void onClickBtnSendDats(View v){
 		controller.check_information(this, ((EditText)findViewById(R.id.text_email)).getText().toString(), ((EditText)findViewById(R.id.text_password)).getText().toString());
 	}
 	
 	public void onClickBtnRegister(View v){
 		cuidaApp.util.AppGlobal.getInstance().dispatcher.open(this, "registrar",
				true);
 	}
 	
 	public void onClickBtnForgetPassword(View v){
 		cuidaApp.util.AppGlobal.getInstance().dispatcher.open(this, "forgetpassword",
				true);
 	}
 	
}

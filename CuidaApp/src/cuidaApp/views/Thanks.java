package cuidaApp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.cuidaapp.R;

public class Thanks extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_thaks);
	}
	
	public void BtnMenu(View v){
		  Intent intent = new Intent(Thanks.this, ActvieGPS.class);
          startActivity(intent);
          finish();
	}
}

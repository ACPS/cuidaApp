package cuidaApp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.cuidaapp.R;

import cuidaApp.controllers.CacheMemoryController;

public class Thanks extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_thaks);
		
		ImageView img = (ImageView)findViewById(R.id.image_fondo);
		CacheMemoryController.getInstance().loadBitmap(R.drawable.fondo, img);
		
		img = (ImageView)findViewById(R.id.img_sended);
		CacheMemoryController.getInstance().loadBitmap(R.drawable.img_enviado, img);
	}
	
	public void BtnMenu(View v){
		  Intent intent = new Intent(Thanks.this, ActvieGPS.class);
          startActivity(intent);
          finish();
	}
}

package cuidaApp.views;

import com.example.cuidaapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Activos extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_activos);
	}
	
	public void DetalleActivo(View v){
		Intent intent = new Intent(Activos.this, Detalle.class);
        startActivity(intent);
        finish();
 	}

	
	
}

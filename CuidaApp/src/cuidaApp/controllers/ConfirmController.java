package cuidaApp.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import restful.DoRest;
import restful.DoRest.Verbs;
import restful.DoRestEventListener;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.models.Activo;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

public class ConfirmController {

	private static ConfirmController instance;
	private List<Activo> activos= new LinkedList<Activo>();
	
	
	private ConfirmController(){
		
	}
	
	public static ConfirmController getInstance(){
		if(instance==null)
			instance = new ConfirmController();
		
		return instance;
	}
	
	
   public void loadActives(final Context context){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id_category", ManagerController.getInstance().getSelectedCategory()+""));
		
	
		CommonGlobals.showProgess(context);
		DoRest restloadCategory = new DoRest(AppConfig.ACTIVES_URL,
				Verbs.POST, params);
		
		restloadCategory.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				
				CommonGlobals.hideProgess();
				CommonGlobals.show_alert(context, context.getString(R.string.error_global));
			}

			@Override
			public void onComplete(int status, String json_data_string) {
				
				if (status == 200) {
				
					JSONObject response = null;

					try {

						response = new JSONObject(json_data_string);

						if (response.getBoolean("status")) {
							CommonGlobals.hideProgess();
							JSONArray categories_json = response.getJSONArray("assets");
							
							for(int i=0;i<categories_json.length();i++){
								JSONObject object=categories_json.getJSONObject(i);
								JSONObject activo = object.getJSONObject("asset");
							
								
								Activo activo_object  = new Activo(activo.getInt("id"),activo.getDouble("lon"),activo.getDouble("lat"),activo.getString("address"),activo.getString("estado"));
								Log.i("activos",categories_json.toString());
								activos.add(activo_object);
								
							}
//							Images.init();
					        AppGlobal.getInstance().dispatcher.open((Activity) context, "report", true);
					        
						} else {
							
							CommonGlobals.show_alert(context, context.getString(R.string.error_global));
							CommonGlobals.hideProgess();
							
						}

					} catch (JSONException e) {
						response = null;
						CommonGlobals.hideProgess();
						CommonGlobals.show_alert(context, context.getString(R.string.error_global));
					}

				} else {
					CommonGlobals.hideProgess();
					CommonGlobals.show_alert(context, context.getString(R.string.error_global));
				}
			}
			
		});
		restloadCategory.call();
	}
   
   public List<Activo> getActivos() {
	return activos;
   }
   
   public void setActivos(List<Activo> activos) {
	this.activos = activos;
   } 
}

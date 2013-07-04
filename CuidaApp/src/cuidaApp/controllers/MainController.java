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
import android.bitmapfun.provider.Categoria;
import android.bitmapfun.provider.Images;
import android.content.Context;
import android.util.Log;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.common.ListenerGPS;
import cuidaApp.models.Activo;
import cuidaApp.models.IconMap;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

public class MainController {

	private List<IconMap> icons = new LinkedList<IconMap>();
	private List<Activo> activos = new LinkedList<Activo>();
	private static MainController instance;
	
	
	public static MainController getInstance(){
		if(instance==null)
			instance = new MainController();
		
		return instance;
	}
	
	private MainController(){
		
	}
	

	public void loadCategory(final Context context){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("lat", ListenerGPS.getInstance().latitud+""));
		params.add(new BasicNameValuePair("lon", ListenerGPS.getInstance().longitud+""));
	
		CommonGlobals.showProgess(context);
		DoRest restloadCategory = new DoRest(AppConfig.CATEGORIES_URL,
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
							JSONArray categories_json = response.getJSONArray("categories");
							
							for(int i=0;i<categories_json.length();i++){
								JSONObject object=categories_json.getJSONObject(i);
								JSONObject category = object.getJSONObject("category");
								Log.i("MainController", category+"");
								Categoria categoty_object = new Categoria(category.getInt("id")+"",category.getString("name"),category.getString("img"),category.getString("normal"),category.getString("reported"),category.getString("attended"),category.getString("repaired"));
								Images.addCategory(categoty_object);
							}
//							Images.init();
					        AppGlobal.getInstance().dispatcher.open((Activity) context, "category", true);
					        
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

	
	public void loadMyActives(final Context context){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", PreferencesController.getInstance().getPreferences("email")));

		CommonGlobals.showProgess(context);
		DoRest restloadCategory = new DoRest(AppConfig.MYACTIVES_URL,
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
							
								
							
								int posicion=isIcon(activo.getString("icon"));
								if(posicion==-1){
									icons.add(new IconMap(activo.getString("icon")));
									posicion=icons.size()-1;
								}
								Activo activo_object  = new Activo(activo.getInt("id"),activo.getDouble("lon"),activo.getDouble("lat"),activo.getString("address"),activo.getString("state"),posicion,activo.getString("category"));
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
	
	public int isIcon(String url){
		for(int i=0;i<icons.size();i++){
			if(icons.get(i).getUrl().equalsIgnoreCase(url)){
				return i;
			}
		}
		
		return -1;
	}
	
	public List<Activo> getActivos() {
		return activos;
	}
	
	public List<IconMap> getIcons() {
		return icons;
	}
}

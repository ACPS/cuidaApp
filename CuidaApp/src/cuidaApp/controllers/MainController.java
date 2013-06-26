package cuidaApp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import restful.DoRest;
import restful.DoRestEventListener;
import restful.DoRest.Verbs;
import android.app.Activity;
import android.bitmapfun.provider.Categoria;
import android.bitmapfun.provider.Images;
import android.content.Context;
import android.util.Log;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

import cuidaApp.common.CommonGlobals;
import cuidaApp.common.ListenerGPS;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

public class MainController {

private final String TAG = "ChangePasswordController";
	
	
	public MainController(){
		
	}
	

	public void loadCategory(final Context context){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("lat", ListenerGPS.getInstance().latitud+""));
		params.add(new BasicNameValuePair("lon", ListenerGPS.getInstance().longitud+""));
	
		CommonGlobals.showProgess(context);
		DoRest restloadCategory = new DoRest(AppConfig.CATEGORIES_URL,
				Verbs.POST, params);
		Trace.i(TAG, "ERROR");
		restloadCategory.setListener(new DoRestEventListener() {

			@Override
			public void onError() {
				Trace.i(TAG, "ERROR");
				CommonGlobals.hideProgess();
				CommonGlobals.show_alert(context, context.getString(R.string.error_global));
			}

			@Override
			public void onComplete(int status, String json_data_string) {
				Trace.i(TAG, "Oncomplete");
				if (status == 200) {
					Trace.i(TAG, "200");
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
							AppGlobal.getInstance().dispatcher.open((Activity) context, "report", true);
						}

					} catch (JSONException e) {
						response = null;
						Trace.i(TAG, "Catch: " + e.getMessage());
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

}

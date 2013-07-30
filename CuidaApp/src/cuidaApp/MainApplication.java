package cuidaApp;

import java.util.HashMap;



import android.app.Application;
import android.content.Context;

import cuidaApp.controllers.CacheMemoryController;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

/**
 * @author ACPS
 * 
 */
public class MainApplication extends Application {

	
	private static Context context;

	public HashMap<String, String> phone_data;

	public void onCreate() {
		super.onCreate();
		AppGlobal.getInstance().initialize(this);
		MainApplication.context = getApplicationContext();

		AppConfig.setProductionEnviroment();
		
		CacheMemoryController.getInstance(getResources());
		
		// // Get relevant data
		//RenewToken.getInstance().sendRenewToken();// por definir
		//phone_data = PhoneUtils.readPhoneData();
		
	//	Trace.i(TAG, "--AA--" + phone_data.get("number"));
		
	}

	public static Context getContext() {
		return MainApplication.context;
	}
}

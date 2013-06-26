/**
 * 
 */
package cuidaApp.controllers;


import android.content.Context;
import android.content.SharedPreferences;
import cuidaApp.MainApplication;


/**
 * @author APALMERAS
 *
 */

public class PreferencesController {
	
	private static PreferencesController instance;
	private static SharedPreferences settings;
	
	public static PreferencesController getInstance(){
		if(instance==null){
			instance= new PreferencesController();
		}
		return instance;
	}
	
	private PreferencesController(){
		settings = MainApplication.getContext().getSharedPreferences("perfil",
				Context.MODE_PRIVATE);
	}
	
	public void addPreferences(String key, String value){
		SharedPreferences.Editor edit = settings.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	public void deletePreferences(String key){
		SharedPreferences.Editor edit = settings.edit();
		
		edit.remove(key);
		edit.commit();
	}
	
	public boolean isPrefences(String key){
		
		return settings.contains(key);
	}
	
	public String getPreferences(String key){
		return settings.getString(key, "");
	}
	
	public void deleteAllPreferences(){
		SharedPreferences.Editor edit = settings.edit();
		
		String help = getPreferences("help");
		String first = getPreferences("first");
		
		boolean helpE = isPrefences("help");
		boolean firstE = isPrefences("first");
		
		edit.clear();
		edit.commit();
		
		if(helpE){
			addPreferences("help", help);
		}
		
		if(firstE){
			addPreferences("first", first);
		}
		
	}
	
}

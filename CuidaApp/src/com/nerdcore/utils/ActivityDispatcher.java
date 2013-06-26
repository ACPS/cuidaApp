package com.nerdcore.utils;

import java.util.HashMap;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class ActivityDispatcher {

	HashMap<String, String> handlers;

	public ActivityDispatcher() {
		handlers = new HashMap<String, String>();
	}

	public void addHandler(String name, String clazz) {
		this.handlers.put(name, clazz);
	}

	public void open(Activity activity, String name, boolean finish,
			Params params) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(handlers.get(name));
			Log.i(name, clazz.toString());
		} catch (ClassNotFoundException e) {
			Log.i(name, e.getMessage());
		}
		// Class clazz = handlers.get(name);
		if (clazz != null) {
			Intent intent = new Intent(activity, clazz);
			Log.i(name, "Intent: " + intent.toString());
			if (params != null) {
				for (NameValuePair param : params) {
					intent.putExtra(param.getName(), param.getValue());
				}
			}
			activity.startActivity(intent);
			if (finish) {
				activity.finish();
			}
		}
	}

	public void open(Activity activity, String name, boolean finish) {
		open(activity, name, finish, null);
	}
}

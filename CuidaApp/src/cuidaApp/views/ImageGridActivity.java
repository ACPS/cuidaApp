package cuidaApp.views;

/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.bitmapfun.util.Utils;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;

import com.example.cuidaapp.BuildConfig;

import cuidaApp.common.Exit;
import cuidaApp.controllers.PreferencesController;
import cuidaApp.util.AppGlobal;



/**
 * Simple FragmentActivity to hold the main {@link ImageGridFragment} and not much else.
 */
public class ImageGridActivity extends FragmentActivity {
    private static final String TAG = "ImageGridActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Utils.enableStrictMode();
        }
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new ImageGridFragment2(), TAG);
            ft.commit();
            
        }
       
      
    }
    public void onclicBtnBack(View v){
		AppGlobal.getInstance().dispatcher.open(this, "main", true);
	}
    
    public void onClickBtnClose(View v){
    	Exit.buildAlertExit(this);
    }
   
    public void onClickBtnOptions(View v){
    	PreferencesController.getInstance().addPreferences("actualActivity", "category");
		AppGlobal.getInstance().dispatcher.open(this, "options", true);
    }
    
    public  Context MyContext(){
    	return this;
    }
}

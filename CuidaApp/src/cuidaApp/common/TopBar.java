/**
 * 
 */
package cuidaApp.common;



import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.cuidaapp.R;

import cuidaApp.controllers.PreferencesController;
import cuidaApp.util.AppGlobal;

/**
 * @author LUSTER
 *
 */
public class TopBar {
	
	public static void initializeTopBar(Context context, int backBtnVisibility, 
			int optionsBtnVisibility, int closeBtnVisibility, final String backActivity, final String actualActivity){
		

		final Activity parent_activity = (Activity) context;
		
		Button btnBack = (Button) parent_activity.findViewById(R.id.top_bar_btn_back);
		btnBack.setVisibility(backBtnVisibility);
		
		Button btnOptions = (Button) parent_activity.findViewById(R.id.top_bar_btn_options);
		btnOptions.setVisibility(optionsBtnVisibility);
		
		Button btnClose = (Button) parent_activity.findViewById(R.id.top_bar_btn_close);
		btnClose.setVisibility(closeBtnVisibility);
		
		if(View.GONE != backBtnVisibility){
			btnBack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AppGlobal.getInstance().dispatcher.open(parent_activity, backActivity, true);
				}
				
			});
		}
		
		if(View.GONE != closeBtnVisibility){
			
			btnClose.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Exit.buildAlertExit(parent_activity);
				}
				
			});
		}
		
		if(View.GONE != optionsBtnVisibility){
			
			btnOptions.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					PreferencesController.getInstance().addPreferences("actualActivity", actualActivity);
					AppGlobal.getInstance().dispatcher.open(parent_activity, "options", true);
				}
				
			});
		}
		
		
	}



}

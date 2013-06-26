/**
 * 
 */
package cuidaApp.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cuidaapp.R;

/**
 * @author LUSTER
 *
 */
public class CommonGlobals {
	
	
	private static	ProgressDialog progress;
	
	public static boolean checkEmail(String email) {
		Pattern p = Pattern.compile("[-\\w\\.]+@\\w+\\.\\w+");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public static void showToast(String message, Context context){
		showToast(message, context, Toast.LENGTH_LONG);
	}
	
	public static void showToast(String message, Context context, int duration){
		Toast toast =
			Toast.makeText(context,	message, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
			toast.show();
	}
	
	public static void show_alert(Context context,String mensaje){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(mensaje)
				.setTitle(R.string.error_title)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.error_acept,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static void showProgess(Context context){
		progress = ProgressDialog.show(context, "",
				  context.getString(R.string.wait_message), true);
	}
	
	public static void hideProgess(){
		progress.dismiss();
	}
	
	 public static void unbindDrawables(View view) {
	        if (view.getBackground() != null) {
	        view.getBackground().setCallback(null);
	        }
	        if (view instanceof ViewGroup) {
	            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
	            unbindDrawables(((ViewGroup) view).getChildAt(i));
	            }
	        ((ViewGroup) view).removeAllViews();
	        }
	 }

	 public static void show_alert_infor(Context context,String mensaje){
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(mensaje)
					.setTitle(R.string.information_title)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setCancelable(false)
					.setPositiveButton(R.string.error_acept,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(final DialogInterface dialog,
										final int id) {
									
								}
							});
			final AlertDialog alert = builder.create();
			alert.show();
		}

}

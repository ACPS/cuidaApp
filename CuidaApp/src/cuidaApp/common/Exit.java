package cuidaApp.common;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.cuidaapp.R;
import com.nerdcore.logs.Trace;

public class Exit {

	private static String TAG="class Exit";
	
	
	public static void buildAlertExit(final Context context) {
		Trace.i(TAG, "salida");
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.dialog_exit_content)
				.setTitle(R.string.dialog_exit_title)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.dialog_exit_btn_ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								
								try{
						
								}catch(NullPointerException npex){
									Trace.e(TAG, "No se habia iniciado algun listener");
								}finally{
									((Activity)context).finish();
								}
								
								
								
							}
						})
				.setNegativeButton(R.string.dialog_exit_btn_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								dialog.cancel();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
}

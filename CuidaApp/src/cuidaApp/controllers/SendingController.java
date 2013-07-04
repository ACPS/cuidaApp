package cuidaApp.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.cuidaapp.R;

import cuidaApp.common.CommonGlobals;
import cuidaApp.common.ListenerGPS;
import cuidaApp.util.AppConfig;
import cuidaApp.util.AppGlobal;

@SuppressLint("HandlerLeak")
public class SendingController {

	private static SendingController instance;
	private MultipartEntity entity;
	private final String TAG = "SendReport Controller";
	private static final int INTERNET_ERROR = 1;
	private static final int GLOBAL_ERROR = 4;
	private static final int COMPLETE_UPLOAD = 2;
	private static final int IMAGE_ERROR = 3;
	private Context context;
	private boolean sending = false;

	public static SendingController getInstance() {
		if (instance == null) {
			instance = new SendingController();
		}
		return instance;
	}

	private SendingController() {

	}

	public void createEntity() {

		// Build params list
		this.entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		boolean entityDone = true;

		try {

			String description = ManagerController.getInstance().getDescription();
			if (description == null) {
				description = "";
			}

			entity.addPart("application_token", new StringBody(
					AppConfig.PUSHER_APP_KEY));
			entity.addPart("auth_token", new StringBody(PreferencesController
					.getInstance().getPreferences("token")));
			entity.addPart("key", new StringBody(AppConfig.PUSHER_KEY));
			entity.addPart("event[longitude]", new StringBody(ManagerController
					.getInstance().getLongitude()));
			entity.addPart("event[latitude]", new StringBody(ManagerController
					.getInstance().getLatitude()));
			entity.addPart("event[device_model]", new StringBody(
					ManagerController.getInstance().PhoneModel + " - "
							+ ManagerController.getInstance().AndroidVersion));
			entity.addPart("event[category_id]", new StringBody("1"));
			entity.addPart("event[address_description]", new StringBody(""));
			entity.addPart("event[data]", new StringBody(""));
			entity.addPart("event[description]", new StringBody(description));
			entity.addPart("event[active_id]", new StringBody(ManagerController.getInstance().getSelectedActivo().getId()+""));

			entityDone = true;

		} catch (UnsupportedEncodingException e) {
			Log.i(TAG, "UnsupportedEncodingException => entityDone = false");
			entityDone = false;
		} finally {

			if (entityDone) {
				// Save the files

				for (Bitmap image : ManagerController.getInstance().getImages()) {

					// verificar codigo steganografia
//					Bitmap image_stegano = Steganography.encondeMessage(image,
//							AppConfig.HashSteganography);
//					
					String imagePath = saveImage(image);
//					image_stegano.recycle();

					Log.i(TAG, "Image saved at: " + imagePath);

					if (imagePath != null) {
						FileBody body = new FileBody(new File(imagePath),
								"image/jpeg");
						String tencodign = body.getTransferEncoding();
						Log.i(TAG, tencodign);
						entity.addPart("event[photos_attributes][][image]",
								body);
					} else {
						Log.e(TAG, "Error guardando imagen");
					}

				}
				System.gc();

			} else {
				responseHandler.sendEmptyMessage(GLOBAL_ERROR);
			}
		}
	}

	private String saveImage(Bitmap imagen) {

		String the_path = Environment.getExternalStorageDirectory()
				+ File.separator + "cuidaApp";
		String uid = UUID.randomUUID().toString();
		String the_file = the_path + File.separator + uid + ".png";

		OutputStream fout = null;
		
		boolean success = (new File(the_path)).mkdir(); 
        if (!success) {
            Log.i("directory not created", "directory not created");
        } else {
            Log.i("directory created", "directory created");
        }
		
		try {

			Log.v("Path", the_file);
			fout = new FileOutputStream(the_file);
			imagen.compress(Bitmap.CompressFormat.PNG, 100, fout);

			fout.flush();
			fout.close();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return the_file;

		// imagen.recycle();

	}

	@SuppressLint("HandlerLeak")
	private Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			getResponse(msg);
		}
	};

	protected void getResponse(Message msg) {

		// AppGlobal.getInstance().hideLoading();

		switch (msg.what) {
		case IMAGE_ERROR:

			break;

		case INTERNET_ERROR:
			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(R.string.dialog_no_internet_content)
					.setTitle(R.string.dialog_no_internet_title)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setCancelable(false)
					.setPositiveButton(R.string.dialog_no_internet_btn_again,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									new SaveInBackground().execute("");
								}
							})
					.setNegativeButton(R.string.dialog_no_internet_btn_cancel,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									dialog.cancel();
									sending = false;
									AppGlobal.getInstance().dispatcher
											.open((Activity) context,
													"confirm", true);
								}
							});
			final AlertDialog alert = builder.create();
			alert.show();

			break;

		case GLOBAL_ERROR:
			final AlertDialog.Builder alerta = new AlertDialog.Builder(context);
			alerta.setMessage(R.string.error_global)
					.setTitle(R.string.error_title)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setCancelable(false)
					.setPositiveButton(R.string.dialog_no_internet_btn_again,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									new SaveInBackground().execute("");
								}
							})
					.setNegativeButton(R.string.dialog_no_internet_btn_cancel,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int id) {
									dialog.cancel();
									sending = false;
									ManagerController.getInstance().clearImages();
									AppGlobal.getInstance().dispatcher
											.open((Activity) context,
													"confirm", true);
								}
							});
			final AlertDialog build = alerta.create();
			build.show();

			break;

		case COMPLETE_UPLOAD:
			// ((Activity)context).finish();

			break;
		}
	}

	public boolean getsending() {
		return sending;
	}

	public HttpResponse postIncident(String url, MultipartEntity entity) {

		HttpResponse response = null;
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		Log.i(TAG, "I'm here HttpResponse");
		sending = true;

		try {
			// Add your data
			httppost.setHeader("Accept", "application/json");
			httppost.setEntity(entity);
			// Execute HTTP Post Request
			response = httpclient.execute(httppost);

			if (response != null) {
				HttpEntity data = response.getEntity();

				// Borrar
				Log.i(TAG, response.getStatusLine().getStatusCode() + "");
				String responseString = EntityUtils.toString(data);
				Log.i(TAG, responseString);

				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {

					try {

						Log.i(TAG, "Try parse JSONObject");
						JSONObject json_data = new JSONObject(responseString);
						Log.i(TAG, "Try parse JSONObject finish");

						String status_response = json_data.getString("status");
						Log.i(TAG, "status_response => " + status_response);

						if (status_response.equals("true")) {
							// mensaje.setText(getString(R.string.thanks_layout_title_ok));
							Log.i(TAG, "ERROR in true!");
							// this.thanks_container.setVisibility(View.VISIBLE);
							ManagerController.getInstance().reset();
							AppGlobal.getInstance().dispatcher.open(
									((Activity) context), "thanks", true);
							ListenerGPS.getInstance().obtenerUbicacion(context);
							sending = false;
							//

						} else {
							responseHandler.sendEmptyMessage(GLOBAL_ERROR);
							sending = false;
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.i(TAG, "ERROR!");
						e.printStackTrace();
						responseHandler.sendEmptyMessage(GLOBAL_ERROR);
						sending = false;
					}

				} else {
					// CommonGlobals.show_alert(context,
					// context.getString(R.string.error_global));
					responseHandler.sendEmptyMessage(INTERNET_ERROR);
					sending = false;
				}

			} else {
				Log.i(TAG, "Response is null");
				CommonGlobals.show_alert(context,
						context.getString(R.string.error_global));
				sending = false;
			}

		} catch (ClientProtocolException e) {
			response = null;
			sending = false;
		} catch (IOException e) {
			response = null;
			sending = false;
		}
		return response;
	}

	public void sendData(Context context) {
		// save the report
		this.context = context;

//		if (Requirements.getInstance().internetReady) {
			new SaveInBackground().execute("");
//		} else {
			//responseHandler.sendEmptyMessage(INTERNET_ERROR);
//		}

	}

	private class SaveInBackground extends AsyncTask<String, Integer, Void> {

		@Override
		protected Void doInBackground(String... params) {

			// Call to post incident
			if (postIncident(AppConfig.ADD_EVENT_URL, entity) != null) {
				Message msg = new Message();
				msg.what = COMPLETE_UPLOAD;
				responseHandler.sendMessage(msg);
			} else {
				responseHandler.sendEmptyMessage(GLOBAL_ERROR);
			}

			return null;
		}

	}

	public void setSending(boolean send) {
		this.sending = send;
	}

}

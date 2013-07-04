package cuidaApp.models;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class IconMap {

	private String url;
	private Bitmap image;
	
	
	public IconMap(String url) {
		super();
		this.url = url;
		new ImageDownloaderIcon().execute(url);
		
	}
	
	public IconMap() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	
	private class ImageDownloaderIcon extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... param) {
			// TODO Auto-generated method stub
			
			return downloadBitmap(param[0]);
		}

		

		@Override
		protected void onPreExecute() {
			Log.i("Async-Example", "onPreExecute Called");


		}

		@Override
		protected void onPostExecute(Bitmap result) {
			Log.i("Async-Example", "onPostExecute Called");
		
		}

		private Bitmap downloadBitmap(String url) {
			// initilize the default HTTP client object
			final DefaultHttpClient client = new DefaultHttpClient();

			//forming a HttoGet request 
			final HttpGet getRequest = new HttpGet(url);
			try {

				HttpResponse response = client.execute(getRequest);

				//check 200 OK for success
				final int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != HttpStatus.SC_OK) {
					Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
					return null;

				}

				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = null;
					try {
						// getting contents from the stream 
						inputStream = entity.getContent();

						// decoding stream data back into image Bitmap that android understands
						
						image=BitmapFactory.decodeStream(inputStream);;
						
						return image;
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
						entity.consumeContent();
					}
				}
			} catch (Exception e) {
				// You Could provide a more explicit error message for IOException
				getRequest.abort();
				Log.e("ImageDownloader", "Something went wrong while retrieving bitmap from " + url + e.toString());
			} 

			return null;
		}

	}
}

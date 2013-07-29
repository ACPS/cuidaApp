package android.bitmapfun.provider;

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

public class Categoria {

	private String id;
	private String name;
	private String url;
	private String icon_normal;
	private String icon_reported;
	private String icon_attended;
	private String icon_repaired;
	private Bitmap normal;
	private Bitmap reported;
	private Bitmap attended;
	private Bitmap repaired;
	
	public Categoria(String id,String name, String url, String icon_normal, String icon_reported,String  icon_attended, String icon_repaired){
		this.id=id;
		this.name=name;
		this.url=url;
		this.icon_normal=icon_normal;
		this.icon_repaired=icon_repaired;
		this.icon_attended=icon_attended;
		this.icon_repaired=icon_repaired;
	}
	
	public Categoria(){
		this.id="";
		this.name="";
		this.url="";
		this.icon_normal="";
		this.icon_repaired="";
		this.icon_attended="";
		this.icon_repaired="";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon_normal() {
		return icon_normal;
	}

	public void setIcon_normal(String icon_normal) {
		this.icon_normal = icon_normal;
	}

	public String getIcon_reported() {
		return icon_reported;
	}

	public void setIcon_reported(String icon_reported) {
		this.icon_reported = icon_reported;
	}

	public String getIcon_attended() {
		return icon_attended;
	}

	public void setIcon_attended(String icon_attended) {
		this.icon_attended = icon_attended;
	}

	public String getIcon_repaired() {
		return icon_repaired;
	}

	public void setIcon_repaired(String icon_repaired) {
		this.icon_repaired = icon_repaired;
	}
	
	public void DownloadIcon(){
		if(normal==null){
			if(icon_normal!=null)
				new ImageDownloaderNormal().execute(icon_normal);
		}
		if(repaired==null){
			if (icon_repaired!=null)
				new ImageDownloaderRepaired().execute(icon_repaired);
		}
		
		if(reported==null){
			if(icon_reported!=null)
				new ImageDownloaderReported().execute(icon_reported);
		}
		
		if(icon_attended==null){
			if(icon_attended!=null)
				new ImageDownloaderAttended().execute(icon_attended);
		}
	}
	private class ImageDownloaderNormal extends AsyncTask<String, Void, Bitmap> {

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
						final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						if(url.equalsIgnoreCase(icon_normal)){
							normal=bitmap;
						}
						if(url.equalsIgnoreCase(icon_attended)){
							attended=bitmap;
						}
						if(url.equalsIgnoreCase(icon_repaired)){
							repaired=bitmap;
						}
						if(url.equalsIgnoreCase(icon_reported)){
							reported=bitmap;
						}
						return bitmap;
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
	
	private class ImageDownloaderRepaired extends AsyncTask<String, Void, Bitmap> {

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
						final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						
						
						if(url.equalsIgnoreCase(icon_repaired)){
							repaired=bitmap;
						}
						
						return bitmap;
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

	private class ImageDownloaderReported extends AsyncTask<String, Void, Bitmap> {

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
						final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						
						
						if(url.equalsIgnoreCase(icon_reported)){
							reported=bitmap;
						}
						
						return bitmap;
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
	
	private class ImageDownloaderAttended extends AsyncTask<String, Void, Bitmap> {

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
						final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						
						
						if(url.equalsIgnoreCase(icon_reported)){
							reported=bitmap;
						}
						
						return bitmap;
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
	
	public Bitmap getNormal() {
		return normal;
	}
	
	public Bitmap getAttended() {
		return attended;
	}
	
	public Bitmap getRepaired() {
		return repaired;
	}
	
	public Bitmap getReported() {
		return reported;
	}

	
}

/**
 * 
 */
package cuidaApp.controllers;

import android.bitmapfun.util.ImageResizer;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.example.cuidaapp.R;





/**
 * @author LUSTER
 *
 */
public class CacheMemoryController {
	
	private LruCache<Integer, Bitmap> memoryCache;
	
	
	private int[] graphicsId = {R.drawable.splash,R.drawable.fondo,R.drawable.help1,R.drawable.help2,R.drawable.help3,
			R.drawable.fondo_noche, R.drawable.input_code, R.drawable.icon_map, R.drawable.btn_update_photo,
			R.drawable.img_enviando
			
			

	};
	
	private static CacheMemoryController instance;
	
	public static CacheMemoryController getInstance(Resources resources){
		if(instance == null)
			instance = new CacheMemoryController(resources);
		return instance;
	}
	
	public static CacheMemoryController getInstance(){
		return instance;
	}
	
	private CacheMemoryController(Resources resources){
		
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		this.memoryCache = new LruCache<Integer, Bitmap>(cacheSize);

		for (Integer id : graphicsId) {
			Bitmap shape = ImageResizer.decodeSampledBitmapFromResource(
					resources, id, 500, 500, null);
			
			this.memoryCache.put(id, shape);
		}

	}
	

	public Bitmap getBitmapFromMemCache(Integer key) {
		return memoryCache.get(key);
	}
	
	public boolean isCache(Integer key){
		Log.i("cache",(memoryCache.get(key)!=null)+"");
		return (memoryCache.get(key)!=null);
	}
	
	public void addCache(Integer key, Bitmap shape){
		Log.i("cache add","here");
		this.memoryCache.put(key, shape);
	}
	
	
	public void loadBitmap(int resId, ImageView imageView) {
		final Bitmap bitmap = getBitmapFromMemCache(resId);
		imageView.setImageBitmap(bitmap);
	}

}

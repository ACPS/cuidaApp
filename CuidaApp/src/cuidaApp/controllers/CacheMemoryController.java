/**
 * 
 */
package cuidaApp.controllers;

import com.example.cuidaapp.R;

import android.bitmapfun.util.ImageResizer;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;





/**
 * @author LUSTER
 *
 */
public class CacheMemoryController {
	
	private LruCache<Integer, Bitmap> memoryCache;
	
	private int[] graphicsId = {R.drawable.help1,R.drawable.help2,R.drawable.help3
			

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
					resources, id, 75, 75, null);
			this.memoryCache.put(id, shape);
		}

	}
	

	public Bitmap getBitmapFromMemCache(Integer key) {
		return memoryCache.get(key);
	}
	
	public void loadBitmap(int resId, ImageView imageView) {

		final Bitmap bitmap = getBitmapFromMemCache(resId);
		imageView.setImageBitmap(bitmap);

	}

}

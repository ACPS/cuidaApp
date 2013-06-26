/**
 * 
 */
package cuidaApp.controllers;

import com.example.cuidaapp.R;

import android.support.v4.app.FragmentManager;
import android.bitmapfun.util.ImageCache.ImageCacheParams;
import android.bitmapfun.util.ImageFetcher;
import android.content.Context;

/**
 * @author APALMERAS
 *
 */
public class InternetCacheController {
	
	private ImageFetcher mImageFetcher;
	
	private static InternetCacheController instance;
	
	public static InternetCacheController getInstance(Context context,
			int mImageThumbSize, FragmentManager fragmetManager,
			ImageCacheParams cacheParams) {
		instance = new InternetCacheController(context, mImageThumbSize, fragmetManager, cacheParams);
		return instance;
	}
	
	public static InternetCacheController getInstance(){
		return instance;
	}
	
	private InternetCacheController(Context context, int mImageThumbSize,
			FragmentManager fragmentManager,
			ImageCacheParams cacheParams) {
		
		mImageFetcher = new ImageFetcher(context, mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(fragmentManager, cacheParams);
		
	}
	
	public ImageFetcher getmImageFetcher() {
		return mImageFetcher;
	}

}

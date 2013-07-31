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




import android.annotation.TargetApi;
import android.bitmapfun.provider.Categoria;
import android.bitmapfun.provider.Images;
import android.bitmapfun.util.ImageCache.ImageCacheParams;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuidaapp.R;

import cuidaApp.controllers.ConfirmController;
import cuidaApp.controllers.InternetCacheController;
import cuidaApp.controllers.ManagerController;
import cuidaApp.controllers.PreferencesController;




/**
 * The main fragment that powers the ImageGridActivity screen. Fairly straight forward GridView
 * implementation with the key addition being the ImageWorker class w/ImageCache to load children
 * asynchronously, keeping the UI nice and smooth and caching thumbnails for quick retrieval. The
 * cache is retained over configuration changes like orientation change so the images are populated
 * quickly if, for example, the user rotates the device.
 */
public class ImageGridFragment2 extends Fragment implements AdapterView.OnItemClickListener {
    
	private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";

    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private AdapterCategory mAdapter;
  
    

    /**
     * Empty constructor as per the Fragment documentation
     */
    public ImageGridFragment2() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
       

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        mAdapter = new AdapterCategory(getActivity());

        ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);

        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
//        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
//        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
//        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
//        TopBar.initializeTopBar(this, View.VISIBLE, View.VISIBLE, View.VISIBLE, "main","category");
    	
		InternetCacheController.getInstance(getActivity(), mImageThumbSize,
				getActivity().getSupportFragmentManager(), cacheParams);
		
		
        
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.activity_category, container, false);
        TextView tv = (TextView)v.findViewById(R.id.help);
        tv.setText(getActivity().getString(R.string.activity_category_intro)+" "+PreferencesController.getInstance().getPreferences("city")+".");
//        Log.i("ciudad",ManagerController.getInstance().getCity()+"-"+tv);
        Button btn_options = (Button)v.findViewById(R.id.top_bar_btn_options);
        if(!PreferencesController.getInstance().isPrefences("active")){
        	btn_options.setVisibility(View.GONE);
        }
        final ListView listView = (ListView) v.findViewById(R.id.listview);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                // Pause fetcher to ensure smoother scrolling when flinging
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    InternetCacheController.getInstance().getmImageFetcher().setPauseWork(true);
                } else {
                	InternetCacheController.getInstance().getmImageFetcher().setPauseWork(false);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				
//				v.setBackgroundColor(R.color.yellow);
				Log.i("a","a");
				AdapterCategory  selected=(AdapterCategory)listView.getItemAtPosition(position);
				v.setBackgroundResource(R.drawable.item);
				}
			
		});

	
    
        
        // This listener is used to get the final width of the GridView and then calculate the
        // number of columns and the width of each column. The width of each column is variable
        // as the GridView has stretchMode=columnWidth. The column width is used to set the height
        // of each view so we get nice square thumbnails.
//        listView.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        if (mAdapter.getNumColumns() == 0) {
//                            final int numColumns = (int) Math.floor(
//                            		listView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
//                            if (numColumns > 0) {
//                                final int columnWidth =
//                                        (listView.getWidth() / numColumns) - mImageThumbSpacing;
//                                mAdapter.setNumColumns(numColumns);
//                                mAdapter.setItemHeight(columnWidth);
//                                if (BuildConfig.DEBUG) {
//                                    Log.d(TAG, "onCreateView - numColumns set to " + numColumns);
//                                }
//                            }
//                        }
//                    }
//                });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        InternetCacheController.getInstance().getmImageFetcher().setExitTasksEarly(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        InternetCacheController.getInstance().getmImageFetcher().setPauseWork(false);
        InternetCacheController.getInstance().getmImageFetcher().setExitTasksEarly(true);
        InternetCacheController.getInstance().getmImageFetcher().flushCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InternetCacheController.getInstance().getmImageFetcher().closeCache();
    }

    @TargetApi(16)
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	Log.i("",getActivity().getApplicationContext()+"");
    	Categoria Cat =(Categoria)parent.getItemAtPosition(position);
    	Cat.DownloadIcon();
    	ManagerController.getInstance().setSelectedCategory(Cat);
    	ConfirmController.getInstance().loadActives(getActivity());
//        final Intent i = new Intent(getActivity(), Actives.class);
////        i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);
//        Categoria Cat =(Categoria)parent.getItemAtPosition(position);
//        Cat.DownloadIcon();
//        ManagerController.getInstance().setSelectedCategory(Cat);
//        Log.i("ImageGridFragment",Cat.getName());
//        if (Utils.hasJellyBean()) {
//            // makeThumbnailScaleUpAnimation() looks kind of ugly here as the loading spinner may
//            // show plus the thumbnail image in GridView is cropped. so using
//            // makeScaleUpAnimation() instead.
//            ActivityOptions options =
//                    ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
//            getActivity().startActivity(i, options.toBundle());
//            getActivity().finish();
//        } else {
//            startActivity(i);
//            getActivity().finish();
//            
//        }
    }

  

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_cache:
            	InternetCacheController.getInstance().getmImageFetcher().clearCache();
                Toast.makeText(getActivity(), R.string.clear_cache_complete_toast,
                        Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The main adapter that backs the GridView. This is fairly standard except the number of
     * columns in the GridView is used to create a fake top row of empty views as we use a
     * transparent ActionBar and don't want the real top row of images to start off covered by it.
     */
    private class AdapterCategory extends BaseAdapter {

        private final Context mContext;
        private int mItemHeight = 0;
        private int mNumColumns = 0;
        private int mActionBarHeight = 0;
        private int id=0;
        private GridView.LayoutParams mImageViewLayoutParams;

        public AdapterCategory(Context context) {
            super();
            mContext = context;
            mImageViewLayoutParams = new GridView.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            // Calculate ActionBar height
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(
                    android.R.attr.actionBarSize, tv, true)) {
                mActionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data, context.getResources().getDisplayMetrics());
            }
        }

        @Override
        public int getCount() {
            // Size + number of columns for top empty row
            return Images.imageThumbUrls.size() + mNumColumns;
        }

        @Override
        public Object getItem(int position) {
            return position < mNumColumns ?
                    null : Images.imageThumbUrls.get(position - mNumColumns);
        }

        @Override
        public long getItemId(int position) {
            return position < mNumColumns ? 0 : position - mNumColumns;
        }

        @Override
        public int getViewTypeCount() {
            // Two types of views, the normal ImageView and the top row of empty views
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return (position < mNumColumns) ? 1 : 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
           
          
            
//            ImageView imageView;
//            if (convertView == null) { // if it's not recycled, instantiate and initialize
//                imageView = new RecyclingImageView(mContext);
//                
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setLayoutParams(mImageViewLayoutParams);
//            } else { // Otherwise re-use the converted view
//                imageView = (ImageView) convertView.findViewById(R.id.image_asset);
//            }
//
//            // Check the height matches our calculated column width
//            if (imageView.getLayoutParams().height != mItemHeight) {
//                imageView.setLayoutParams(mImageViewLayoutParams);
//            }

            // Finally load the image asynchronously into the ImageView, this also takes care of
            // setting a placeholder image while the background thread runs
           // InternetCacheController.getInstance().getmImageFetcher().loadImage(Images.imageThumbUrls.get(position - mNumColumns).getUrl(), imageView);
            
    	  View view; 
          LayoutInflater inflater = (LayoutInflater)   getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
          view = inflater.inflate(R.layout.adapter_category2, null);
          ImageView image=(ImageView)view.findViewById(R.id.image_view_category);
          TextView name= (TextView)view.findViewById(R.id.name_Category);
          name.setText(Images.imageThumbUrls.get(position - mNumColumns).getName());
        
	        
	        //COMO ANTES
	        
	      
//	        // Now handle the main ImageView thumbnails
//	        ImageView imageView;
//	        if (convertView == null) { // if it's not recycled, instantiate and initialize
//	            imageView = new RecyclingImageView(mContext);
//	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//	            imageView.setLayoutParams(mImageViewLayoutParams);
//	        } else { // Otherwise re-use the converted view
//	            imageView = (ImageView) convertView;
//	        }
//	
//	        // Check the height matches our calculated column width
//	        if (imageView.getLayoutParams().height != mItemHeight) {
//	            imageView.setLayoutParams(mImageViewLayoutParams);
//	        }
//	
//	        // Finally load the image asynchronously into the ImageView, this also takes care of
//	        // setting a placeholder image while the background thread runs
          	image.setBackgroundResource(android.R.color.transparent);
	        InternetCacheController.getInstance().getmImageFetcher().loadImage(Images.imageThumbUrls.get(position - mNumColumns).getUrl(), image);
	      
	        ImageButton btn_go_category= (ImageButton)view.findViewById(R.id.btn_go_category);
	        final int pos =position;
	        btn_go_category.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
						Categoria Cat =Images.imageThumbUrls.get(pos - mNumColumns);
				    	Cat.DownloadIcon();
				    	ManagerController.getInstance().setSelectedCategory(Cat);
				    	ConfirmController.getInstance().loadActives(getActivity());
				}
			});
	       
	    	
	        return view;
        }

        /**
         * Sets the item height. Useful for when we know the column width so the height can be set
         * to match.
         *
         * @param height
         */
        public void setItemHeight(int height) {
            if (height == mItemHeight) {
                return;
            }
            mItemHeight = height;
            mImageViewLayoutParams =
                    new GridView.LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
            InternetCacheController.getInstance().getmImageFetcher().setImageSize(height);
            notifyDataSetChanged();
        }

        public void setNumColumns(int numColumns) {
            mNumColumns = numColumns;
        }

        public int getNumColumns() {
            return mNumColumns;
        }
    }
}
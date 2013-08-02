package cuidaApp.views;




import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.cuidaapp.R;

import cuidaApp.controllers.CacheMemoryController;
import cuidaApp.controllers.PreferencesController;


public class FirstHelp extends  SherlockFragmentActivity  {
	static final int NUM_ITEMS = 3;
 
	
    
 
    ViewPager mPager;    
    MyAdapter mAdapter;

    
    
    private static int layouts[] = {
    		R.layout.help_one,R.layout.help_two,R.layout.help_three
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(SampleList.THEME); //Used for theme switching in samples
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    
        
        setContentView(R.layout.fragment_pager);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        
        
        
        
   

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
       
        
        
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
        	  @Override
        	  public void onPageSelected(int index) {
        	    
        	   
        	  }

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
        	 
        });
        
       
        
    }
    
    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }
    }

    public static class ArrayListFragment extends SherlockFragment {
        
    	int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            
        	ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
        	
            super.onCreate(savedInstanceState);
            
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//            buildNav();
            
        }
        
//        private void buildNav(){
//        	
//        	next = mNum;
//            back = mNum;
//            
//            
//            
//        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	View v = inflater.inflate(layouts[mNum], container, false);
        	ImageView image =(ImageView) v.findViewById(R.id.image_help_1);
        	
        	if(mNum==0){
        		image =(ImageView) v.findViewById(R.id.image_help_1);
        		CacheMemoryController.getInstance().loadBitmap(R.drawable.help1, image);
        	}else if(mNum==1){
        		image =(ImageView) v.findViewById(R.id.image_help_2);
        		CacheMemoryController.getInstance().loadBitmap(R.drawable.help2, image);
        	}else{
        		image =(ImageView) v.findViewById(R.id.image_help_3);
        		CacheMemoryController.getInstance().loadBitmap(R.drawable.help3, image);
        	}
            
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        	
        	mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//        	buildNav();
        	
            super.onActivityCreated(savedInstanceState);
        }

        
        public static class MyAdapter extends FragmentPagerAdapter {
            
        	int position;
        	
        	public MyAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public int getCount() {
                return NUM_ITEMS;
            }

            @Override
            public Fragment getItem(int position) {

                return ArrayListFragment.newInstance(position);
            }
            
            
           
        }

        public static class ArrayWListFragment extends SherlockFragment {
            
        	int mNum;

            /**
             * Create a new instance of CountingFragment, providing "num"
             * as an argument.
             */
            static ArrayListFragment newInstance(int num) {
                
            	ArrayListFragment f = new ArrayListFragment();

                // Supply num input as an argument.
                Bundle args = new Bundle();
                args.putInt("num", num);
                f.setArguments(args);

                return f;
            }

            /**
             * When creating, retrieve this instance's number from its arguments.
             */
            @Override
            public void onCreate(Bundle savedInstanceState) {
            	
                super.onCreate(savedInstanceState);
                
                mNum = getArguments() != null ? getArguments().getInt("num") : 1;

                
            }


            /**
             * The Fragment's UI is just a simple text view showing its
             * instance number.
             */
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
            	
            	View v = inflater.inflate(layouts[mNum], container, false);
            	
                
                return v;
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
            	
            	mNum = getArguments() != null ? getArguments().getInt("num") : 1;
                super.onActivityCreated(savedInstanceState);
            }

            
           
        }
           
    }
    public void closeHelp(View v){
    	PreferencesController.getInstance().addPreferences("first", "first");
    	Intent intent = new Intent(FirstHelp.this, ActvieGPS.class);
        startActivity(intent);
    	finish();
    	
    }
  
    
  
    
}

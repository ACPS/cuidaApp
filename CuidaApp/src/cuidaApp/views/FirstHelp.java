package cuidaApp.views;




import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.cuidaapp.R;

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
        
        
        Log.i("Main","onCreate");
        
   

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
       
        
        
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
        	  @Override
        	  public void onPageSelected(int index) {
        	    Log.i("e", "onPageSelected " + index);
        	   
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
        	Log.i("Main", "OnCreateFragment"+mNum);
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
        	Log.i("onCreateView", "onCreateView"+mNum);
            View v = inflater.inflate(layouts[mNum], container, false);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        	Log.i("Main", "OnActivityCreated"+mNum);
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
            	
            	//super.getItemId(position);
            	
            	Log.i("Main", "getItem"+position);
            	
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
            	Log.i("Main", "OnCreateFragment"+mNum);
                super.onCreate(savedInstanceState);
                
                mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//                buildNav();
                
            }
            
//            private void buildNav(){
//            	
//            	next = mNum;
//                back = mNum;
//                
//                
//                
//            }

            /**
             * The Fragment's UI is just a simple text view showing its
             * instance number.
             */
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
            	Log.i("onCreateView", "onCreateView"+mNum);
            	View v = inflater.inflate(layouts[mNum], container, false);
            	
                
                return v;
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
            	Log.i("Main", "OnActivityCreated"+mNum);
            	mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//            	buildNav();
            	
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

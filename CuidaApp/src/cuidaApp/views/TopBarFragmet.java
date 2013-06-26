/**
 * 
 */
package cuidaApp.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cuidaapp.R;

/**
 * @author LUSTER
 *
 */
public class TopBarFragmet extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
			return inflater.inflate(R.layout.top_bar, container, false);
    }
	
}

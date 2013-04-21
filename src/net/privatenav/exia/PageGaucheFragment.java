package net.privatenav.exia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PageGaucheFragment extends Fragment {

	private String the_pseudo = null; 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 View V = inflater.inflate(R.layout.page_gauche_layout, container, false);
     try {
    	the_pseudo = new Details_eleve().passeur();
 		TextView _test = (TextView)V.findViewById(R.id.thepseudo);
 		_test.setText(the_pseudo);
 	Log.e("loh_tag", the_pseudo);
	} catch (Exception e) {
		
	        Log.e("log_tag", "Error  "+the_pseudo);
	
	}
		
		
		return V;
	}
	
	
	
	/*@Override
	public void onStart() {
		the_pseudo = new Details_eleve().passeur();
		TextView _test = (TextView)getActivity().findViewById(R.id.thepseudo);
		_test.setText(the_pseudo);
		super.onStart();
	}*/
}
package net.privatenav.exia;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class Send_sms extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 View V = inflater.inflate(R.layout.activity_send_sms, container, false);
		
    
		return V;
    }
}

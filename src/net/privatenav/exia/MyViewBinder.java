package net.privatenav.exia;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;
 
public class MyViewBinder implements ViewBinder {
	public boolean setViewValue(View view, Object mylist,String textRepresentation) {
		if( (view instanceof ImageView) & (mylist instanceof Bitmap) ) {
			ImageView iv = (ImageView) view;
			Bitmap bm = (Bitmap) mylist;	
			iv.setImageBitmap(bm);	
			return true;
		}
 
		return false;
	}
}
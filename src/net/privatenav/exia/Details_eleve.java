package net.privatenav.exia;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


public class Details_eleve extends FragmentActivity  {

	private static String pseudo = null;
	private PagerAdapter mPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_eleve);
        
        Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    	    pseudo = extras.getString("pseudo");
    	}
    	TextView _pseudo = (TextView) findViewById(R.id.textView2);
    	_pseudo.setText("@"+pseudo);
    	
    	
    
    	// Création de la liste de Fragments que fera défiler le PagerAdapter
    			List fragments = new Vector();
    			Bundle args = new Bundle();
    		    args.putString("someString", pseudo);

    		   
    			// Ajout des Fragments dans la liste
    			fragments.add(Fragment.instantiate(this,PageGaucheFragment.class.getName()));
    			fragments.add(Fragment.instantiate(this,PageMilieuFragment.class.getName()));
    			fragments.add(Fragment.instantiate(this,PageDroiteFragment.class.getName()));
    			

    			// Création de l'adapter qui s'occupera de l'affichage de la liste de
    			// Fragments
    			this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

    			ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
    			// Affectation de l'adapter au ViewPager
    			pager.setAdapter(this.mPagerAdapter);
    			/*String tempp = passeur();
    			Toast.makeText(Details_eleve.this,tempp ,Toast.LENGTH_LONG).show();*/
    			

    }
    
    public static String passeur()
    {
    	
    	return pseudo;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

    	private final List fragments;

    	//On fournit à l'adapter la liste des fragments à afficher
    	public MyPagerAdapter(FragmentManager fm, List fragments) {
    		super(fm);
    		this.fragments = fragments;
    		
    	}

    	@Override
    	public Fragment getItem(int position) {
    		return (Fragment) this.fragments.get(position);
    	}

    	@Override
    	public int getCount() {
    		return this.fragments.size();
    	}
    }
    
    
    
    
    
   
}

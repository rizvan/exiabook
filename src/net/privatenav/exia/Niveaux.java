package net.privatenav.exia;

import java.util.List;
import java.util.Vector;

import net.privatenav.exia.Pp_menu.MyPagerAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;


public class Niveaux extends FragmentActivity {
	private PagerAdapter mPagerAdapter;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_niveaux);
		
		 // Création de la liste de Fragments que fera défiler le PagerAdapter
		List fragments = new Vector();
		Bundle args = new Bundle();
	    args.putString("someString", "genyus");

	   
		// Ajout des Fragments dans la liste
		fragments.add(Fragment.instantiate(this,Web.class.getName()));
		fragments.add(Fragment.instantiate(this,Programmation.class.getName()));
		fragments.add(Fragment.instantiate(this,Divers.class.getName()));
		

		// Création de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
		// Affectation de l'adapter au ViewPager
		pager.setAdapter(this.mPagerAdapter);
		/*String tempp = passeur();
		Toast.makeText(Details_eleve.this,tempp ,Toast.LENGTH_LONG).show();*/
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

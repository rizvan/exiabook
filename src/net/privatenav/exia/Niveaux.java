package net.privatenav.exia;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


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

		ImageButton _maj = (ImageButton)findViewById(R.id.imageButton1);
		_maj.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Niveaux.this);
				builder.setCancelable(true);
				builder.setItems(R.array.maj, new DialogInterface.OnClickListener(){
		               public void onClick(DialogInterface dialog, int which) {
		            	   switch (which) {
						case 0:
							AlertDialog.Builder builder1 = new AlertDialog.Builder(Niveaux.this);
					        builder1.setMessage("Mettre à jour vos compétences ?")
					               .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
					                   public void onClick(DialogInterface dialog, int id) {
					                	 
					   							  
					   						/*CAsyncTask2 task2 = new CAsyncTask2 ();
					   						task2.execute(o.get("idfix").toString());
					   						regenerer();*/
					                   }
					               })
					               .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
					                   public void onClick(DialogInterface dialog, int id) {
					                       // User cancelled the dialog
					                   }
					               });
						    builder1.create();
						    builder1.show();
							break;
						}
		            	
		               }
		        });
				
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
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

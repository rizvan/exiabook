package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.agimind.widget.SlideHolder;

import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Pp_menu extends FragmentActivity {
	
	private static String nom = null;
	private static String prenom = null;
	private static String pseudo = null;
	private static String photo_p = null;
	private static String id = null;
	Spinner spinner;
	private Pp_menu activity;
	private PagerAdapter mPagerAdapter;
	private SlideHolder mSlideHolder;
	
	/*
	 * 
	 * Nouvelle class pour le DL de la photo de profil
	 */
	public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pp_menu);
        
        mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
        
        Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    	    nom = extras.getString("nom");
    	    prenom = extras.getString("prenom");
    	    pseudo = extras.getString("pseudo");
    	    photo_p = extras.getString("photo");
    	    id = extras.getString("idé");
    	}
    	Log.e("log_tag", id.toString());
    	Log.e("log_tag_photo", id.toString());
    	
    	activity = this;
    	
    	CAsyncTask task = new CAsyncTask ();
        task.execute (id.toString());
    	final Context context = this;
    	
    	 TextView _Nom = (TextView)findViewById(R.id.textView1);
         _Nom.setText(nom + "  " + prenom);
         TextView _Pseudo = (TextView)findViewById(R.id.textView2);
         _Pseudo.setText("@"+pseudo);
         
         TextView _NomRibbon = (TextView)findViewById(R.id.nom);
         _NomRibbon.setText(nom);
         TextView _PrenomRibbon = (TextView)findViewById(R.id.prenom);
         _PrenomRibbon.setText(prenom);
         TextView _PseudoRibbon = (TextView)findViewById(R.id.mypseudo);
         _PseudoRibbon.setText("@"+pseudo);
         
         
         View toggleView = findViewById(R.id.button2);
 		toggleView.setOnClickListener(new View.OnClickListener() {
 			
 			public void onClick(View v) {
 				mSlideHolder.toggle();
 			}
 		});
       
      //pour publier quelque chose
         ImageButton _publish = (ImageButton) findViewById(R.id.imageButton2);
         _publish.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent myIntent4 = new Intent(Pp_menu.this, Publishing.class);
				myIntent4.putExtra("prenom", prenom);
  	    	 	myIntent4.putExtra("nom", nom);
  	    	 	myIntent4.putExtra("pseudo",pseudo);
  	    	 	myIntent4.putExtra("photo", photo_p);
  	    	 	myIntent4.putExtra("idé", id);
  	    	 	Pp_menu.this.startActivity(myIntent4);
			}
		});
         
      //pour competence ribbon
      Button _CompRibbon = (Button)findViewById(R.id.Button06);
      _CompRibbon.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			try {
				Intent myIntent = new Intent(Pp_menu.this, Niveaux.class);
				myIntent.putExtra("prenom", prenom);
				myIntent.putExtra("nom", nom);
		    	myIntent.putExtra("pseudo",pseudo);
		    	myIntent.putExtra("photo", photo_p);
		    	myIntent.putExtra("idé", id);
		    	Pp_menu.this.startActivity(myIntent);
			} catch (Exception e) {
				Log.e("log_tag", "error ribbon");
			}
			
		}
	});
      
      //acturibbon
      Button _ActuRibbon = (Button)findViewById(R.id.Button02);
      _ActuRibbon.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			try {
				Intent myIntent = new Intent(Pp_menu.this, Actu.class);
				myIntent.putExtra("prenom", prenom);
		    	myIntent.putExtra("nom", nom);
		    	myIntent.putExtra("pseudo",pseudo);
		    	myIntent.putExtra("photo", photo_p);
		    	myIntent.putExtra("idé", id);
		    	Pp_menu.this.startActivity(myIntent);
			} catch (Exception e) {
				Log.e("log_tag", "error ribbon");
			}
			
		}
	});
      
      //publier ribbon
      Button _PublierRibbon = (Button)findViewById(R.id.Button03);
      _PublierRibbon.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			try {
				Intent myIntent = new Intent(Pp_menu.this, Publishing.class);
				myIntent.putExtra("prenom", prenom);
		    	myIntent.putExtra("nom", nom);
		    	myIntent.putExtra("pseudo",pseudo);
		    	myIntent.putExtra("photo", photo_p);
		    	myIntent.putExtra("idé", id);
		    	Pp_menu.this.startActivity(myIntent);
			} catch (Exception e) {
				Log.e("log_tag", "error ribbon");
			}
			
		}
	});
       
      //pour les paramètres
         ImageButton _parametres = (ImageButton) findViewById(R.id.imageButton1);
         _parametres.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(Pp_menu.this);
				builder.setCancelable(true);
				builder.setItems(R.array.spinnerItems, new DialogInterface.OnClickListener(){
		               public void onClick(DialogInterface dialog, int which) {
		            	   switch (which) {
						case 0:
							
							break;

						case 1 :
							break;
							
						case 2 :
							Intent myIntent5 = new Intent(Pp_menu.this, Niveaux.class);
							myIntent5.putExtra("prenom", prenom);
			  	    	 	myIntent5.putExtra("nom", nom);
			  	    	 	myIntent5.putExtra("pseudo",pseudo);
			  	    	 	myIntent5.putExtra("photo", photo_p);
			  	    	 	myIntent5.putExtra("idé", id);
			  	    	 	Pp_menu.this.startActivity(myIntent5);
							break;
							
						case 3 :
							break;
						}
		            	
		               }
		        });
				
				AlertDialog alert = builder.create();
				alert.show();
				
			}
		});
         
      //buttonRibbon promo
        Button _Promo = (Button) findViewById(R.id.Button01);
        _Promo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent myIntent = new Intent(Pp_menu.this, List_promo.class);
				myIntent.putExtra("prenom", prenom);
  	    	 	myIntent.putExtra("nom", nom);
  	    	 	myIntent.putExtra("pseudo",pseudo);
  	    	 	myIntent.putExtra("photo", photo_p);
  	    	 	myIntent.putExtra("idé", id);
  	    	 	Pp_menu.this.startActivity(myIntent);
			}
		});
         
      // show The Image
     	new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute(photo_p);
     	
     //thumb image ribbon menu
     	new DownloadImageTask((ImageView) findViewById(R.id.photopp)).execute(photo_p);
     	
     	Button _flux = (Button) findViewById(R.id.flux_actu);
     	_flux.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent4 = new Intent(Pp_menu.this, Actu.class);
				myIntent4.putExtra("prenom", prenom);
  	    	 	myIntent4.putExtra("nom", nom);
  	    	 	myIntent4.putExtra("pseudo",pseudo);
  	    	 	myIntent4.putExtra("photo", photo_p);
  	    	 	myIntent4.putExtra("idé", id);
  	    	 	Pp_menu.this.startActivity(myIntent4);
			}
		});
     	
     // Création de la liste de Fragments que fera défiler le PagerAdapter
		List fragments = new Vector();
		Bundle args = new Bundle();
	    args.putString("someString", pseudo);

	   
		// Ajout des Fragments dans la liste
		fragments.add(Fragment.instantiate(this,My_actu.class.getName()));
		//fragments.add(Fragment.instantiate(this,List_promo.class.getName()));
		//fragments.add(Fragment.instantiate(this,Send_sms.class.getName()));
		

		// Création de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
		// Affectation de l'adapter au ViewPager
		pager.setAdapter(this.mPagerAdapter);
		/*String tempp = passeur();
		Toast.makeText(Details_eleve.this,tempp ,Toast.LENGTH_LONG).show();*/
		
    }

    @Override
    protected void onRestart() {
    	
    	activity = this;
    	CAsyncTask task = new CAsyncTask ();
        task.execute (id.toString());
    	// TODO Auto-generated method stub
    	super.onRestart();
    }
    
    public static String getid()
    {
    	return id;
    }
    
    public static String getprenom()
    {
    	return prenom;
    }
    
    public static String getnom()
    {
    	return nom;
    }
    
    public static String getphoto()
    {
    	return photo_p;
    }
    
    public static String getpseudo()
    {
    	return pseudo;
    }
    
private class CAsyncTask extends AsyncTask<String, Void, String> {
        
	
    protected String doInBackground (String... args) {
        	String result = null;
        	String global = null;
			//the year data to send
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id",args[0]));
			
			InputStream is = null;
			//http post
			try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_count.php");
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        HttpResponse response = httpclient.execute(httppost);
			        HttpEntity entity = response.getEntity();
			         is = entity.getContent();
			}catch(Exception e){
			        Log.e("log_tag", "Error in http connection "+e.toString());
			}
			//convert response to string
			try{
			        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			        StringBuilder sb = new StringBuilder();
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			        }
			        is.close();
			 
			        result=sb.toString();
			}catch(Exception e){
			        Log.e("log_tag", "Error converting result "+e.toString());
			}
			 
			//parse json data
			try{
	    		JSONArray jArray = new JSONArray(result);
	        	JSONObject json_data=null;
	        	for(int i=0;i<jArray.length();i++)
	        	{
	               json_data = jArray.getJSONObject(i);
	               
	  	    	 	global = json_data.getString("total").toString();	  	    	 	
	           }
	        }
	        catch(JSONException e1){
	        	Log.e("log_tag", "Error "+e1.toString());
	        	
	        } catch (ParseException e1) {
	        	//Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
	        	Log.e("log_tag", "Error "+e1.toString());
	  	}
            return global;
        } // doInBackground ();
        
        @Override
        protected void onPostExecute (String the_final_int) {
        	TextView _compteur = (TextView) findViewById(R.id.textView3);
	    	_compteur.setText(the_final_int);
        } // onPostExecute ();

	
        
    } // CAsyncTask ();
 
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

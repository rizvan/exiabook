package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Actu extends ListActivity {
	
	private String nom = null;
	private String prenom = null;
	private String pseudo = null;
	private String photo_p = null;
	private String id = null;
	private String idfix = null;
	private ListView lv;
	
	private ProgressBar mProgressBar;
	private Actu activity;
	private Context ctx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actu);
		
		Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    	    nom = extras.getString("nom");
    	    prenom = extras.getString("prenom");
    	    pseudo = extras.getString("pseudo");
    	    photo_p = extras.getString("photo");
    	    id = extras.getString("idé");
    	}
    	activity = this;
    	
    	String message = "Merci de bien vouloir patienter...";
        Toast.makeText (this, message, Toast.LENGTH_LONG).show ();
        
        
       
        /*
         * 
         * Recuperation des publications
         */
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        mProgressBar.setVisibility(View.VISIBLE);
        CAsyncTask task = new CAsyncTask ();
        task.execute ();


        /*
    	 * 
    	 * Chaque actu
    	 */
        
        
    	
    	lv = (ListView) findViewById(android.R.id.list);
    	lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(arg2);	        		
        		//Toast.makeText(Actu.this, "L'item '" + o.get("idfix").toString() + "' was clicked.", Toast.LENGTH_SHORT).show();
        		Log.e("log_actuclick", "j'ai cliqué ça marche");
        		
        		Intent myIntent4 = new Intent(Actu.this, commentaires.class);
  	    	 	myIntent4.putExtra("idfix", o.get("idfix").toString());
  	    	 	myIntent4.putExtra("nom", nom.toString());
  	    	 	myIntent4.putExtra("prenom", prenom.toString());
  	    	 	myIntent4.putExtra("photo_p", photo_p.toString());
  	    	 	myIntent4.putExtra("id", id.toString());
  	    	 	myIntent4.putExtra("pseudo", pseudo.toString());
  	    	 	Actu.this.startActivity(myIntent4);
			}
		});
        
    	//le bouton de fin
        final Button btnAddMore = new Button(this);
        btnAddMore.setText("Plus de publications");
        btnAddMore.setBackgroundColor(Color.TRANSPARENT);
        btnAddMore.setTextColor(Color.DKGRAY);
        Drawable img = Actu.this.getApplicationContext().getResources().getDrawable(R.drawable.addicon);
        img.setBounds( 0, 0, 60, 60 );
        btnAddMore.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null );
        btnAddMore.setHeight(160);
        lv.addFooterView(btnAddMore);
    	
        /*
         * 
         * Mise à jour des publications
         */
    	ImageButton _maj = (ImageButton) findViewById(R.id.imageButton1);
    	_maj.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String message = "Mise à jour en cours...";
		        Toast.makeText (Actu.this.getApplicationContext(), message, Toast.LENGTH_LONG).show ();
		        
		        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		        mProgressBar.setVisibility(View.VISIBLE);
		        CAsyncTask task = new CAsyncTask ();
		        task.execute ();
			}
		});
    	
    	/*
    	 * 
    	 * On retourne en arrière
    	 */
    	ImageButton _back = (ImageButton) findViewById(R.id.back);
    	_back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Actu.this.finish();
			}
		});
    	
    	
	}

	
private class CAsyncTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>> {
        protected ArrayList<HashMap<String, Object>> doInBackground (Void... unused) {
        	String result = "";
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		InputStream is = null;
    		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    		//http post
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_messages.php");
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
                   HashMap<String, Object> map;
                   map = new HashMap<String, Object>();
                   
                   map.put("name", "@"+json_data.getString("pseudo")+"("+json_data.getString("nom") + " "+json_data.getString("prenom")+")");
                   map.put("message", json_data.getString("message"));
                   map.put("date", json_data.getString("date"));
                   map.put("photo", json_data.getString("photo"));
                   map.put("idfix", json_data.getString("message_id"));
                   map.put("com", json_data.getString("nbcommentaires"));
                   idfix = json_data.getString("message_id");
    	        	URL aURL = null;
    				try {
    					aURL = new URL(json_data.getString("photo"));
    				} catch (MalformedURLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} 

    	        	Bitmap bitmap = null;
    				try {
    					bitmap = BitmapFactory.decodeStream(aURL.openStream());
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}

    	        	//bitmap.getScaledHeight(200);
    	        	//bitmap.getScaledWidth(100);
    	        	map.put("photo", bitmap);
                   listItem.add(map);
               }
            	return listItem;
            	
            }
            catch(JSONException e1){
            	Log.e("log_tag", "Error "+e1.toString());
            } catch (ParseException e1) {
            	Log.e("log_tag", "Error "+e1.toString());
      	}
            
            return null;
        } // doInBackground ();
        
        protected void onPostExecute (ArrayList<HashMap<String, Object>> _list) {
            String message = "Liste mise à jour avec succès.";
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText (activity, message, Toast.LENGTH_SHORT).show ();
        	SimpleAdapter adapter = new SimpleAdapter(Actu.this, _list, R.layout.actu_timeline, new String[] {"name", "message", "date", "photo", "com"}, new int[] {R.id.allpseudo, R.id.message, R.id.the_date, R.id.thumb_photo, R.id.count});
        	adapter.setViewBinder(new MyViewBinder());
        	lv.setAdapter(adapter);        	
        } // onPostExecute ();
        
    } // CAsyncTask ();




}

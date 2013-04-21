package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Liste_eleves extends ListActivity {
	private String promo = null;
	private String nom = null;
	private String prenom = null;
	private String pseudo = null;
	private String photo_p = null;
	private int id = 0;
	private ListView lv;
	private ProgressBar mProgressBar;
	private Liste_eleves activity;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_eleves);
        
        Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    	    promo = extras.getString("promo");
    	}
    	
        activity = this;
        
        String message = "Merci de bien vouloir patienter...";
        Toast.makeText (this, message, Toast.LENGTH_LONG).show ();
        
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        mProgressBar.setVisibility(View.VISIBLE);
        CAsyncTask task = new CAsyncTask ();
        task.execute(promo);
        
        lv = (ListView) findViewById(android.R.id.list);
    	lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				/*Intent myIntent = new Intent(Liste_eleves.this, Details_eleve.class);
				myIntent.putExtra("id", id);
  	    	 	Liste_eleves.this.startActivity(myIntent);*/
				@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(arg2);	        		
        		/*Toast.makeText(Liste_eleves.this, "NAME '" + o.get("ide").toString() + "' was clicked.", Toast.LENGTH_SHORT).show();*/ 
				Intent myIntent = new Intent(Liste_eleves.this, Details_eleve.class);
				myIntent.putExtra("pseudo", o.get("pseudo"));
  	    	 	Liste_eleves.this.startActivity(myIntent);
}
		});
    }

private class CAsyncTask extends AsyncTask<String, Void, ArrayList<HashMap<String, Object>>> {
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        protected ArrayList<HashMap<String, Object>> doInBackground (String... args) {
        	String result = "";
    		//the year data to send
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		nameValuePairs.add(new BasicNameValuePair("promo", args[0]));
    		InputStream is = null;
    		ArrayList resultats = new ArrayList();
    		List r = new ArrayList();
    		//http post
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requetes_liste_par_promo.php");
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
                   
                   map.put("name",json_data.getString("nom") + " "+json_data.getString("prenom"));
                   map.put("pseudo", json_data.getString("pseudo"));
                   nom = json_data.getString("nom");
                   prenom = json_data.getString("prenom");
                   pseudo = json_data.getString("pseudo");
                   photo_p = json_data.getString("photo");
                   id =json_data.getInt("id");
                   
                   map.put("photo", json_data.getString("photo"));
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
        	if (_list == null)
        	{
        		Toast.makeText (activity, "Cette catégories est vide, invitez un exar à l'inaugurer", Toast.LENGTH_SHORT).show ();
        	}
        	else
        	{
            String message = "Liste mise à jour avec succès.";
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText (activity, message, Toast.LENGTH_SHORT).show ();
            SimpleAdapter adapter = new SimpleAdapter(Liste_eleves.this, _list, R.layout.list, new String[] {"photo", "name", "pseudo"}, new int[] {R.id.img, R.id.prenom, R.id.pseudo});
        	adapter.setViewBinder(new MyViewBinder());
        	lv.setAdapter(adapter);
        	}
        } // onPostExecute ();
        
    } // CAsyncTask ();

}

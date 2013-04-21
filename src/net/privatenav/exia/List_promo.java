package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class List_promo extends Activity {
		
	private ListView lv;
	private ProgressBar mProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_promo);
		try {
	    	 CAsyncTask task = new CAsyncTask ();
	         task.execute();
	     } catch (Exception e) {
		        Log.e("log_tag_lol", "Error");
		}
		
		
		 /*
         * 
         * Mise à jour des publications
         */
    	ImageButton _maj = (ImageButton) findViewById(R.id.imageButton1);
    	_maj.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String message = "Mise à jour en cours...";
		        Toast.makeText (List_promo.this.getApplicationContext(), message, Toast.LENGTH_LONG).show ();
		        
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
				List_promo.this.finish();
			}
		});
    	
    	display();
	}
	
	@Override
	protected void onRestart() {
		CAsyncTask task = new CAsyncTask ();
        task.execute ();
		super.onRestart();
	}
	
	
	
	private void display()
	{
		final
		Context context = this.getApplicationContext();
		lv = (ListView) findViewById(android.R.id.list);
	     lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
				
							switch((int) lv.getItemIdAtPosition((int) arg3))
							{
							case 0 :
								Intent myIntent = new Intent(List_promo.this, Liste_eleves.class);
								myIntent.putExtra("promo", "A1");
								List_promo.this.startActivity(myIntent);
								Log.e("log_button", "Error A1 ");
								break;
							case 1 :
								Intent myIntent1 = new Intent(List_promo.this, Liste_eleves.class);
								myIntent1.putExtra("promo", "A2");
								List_promo.this.startActivity(myIntent1);
								break;
							case 2 :
								Intent myIntent2 = new Intent(List_promo.this, Liste_eleves.class);
								myIntent2.putExtra("promo", "A3");
								List_promo.this.startActivity(myIntent2);
								break;
							case 3 :
								Intent myIntent3 = new Intent(List_promo.this, Liste_eleves.class);
								myIntent3.putExtra("promo", "A4");
								List_promo.this.startActivity(myIntent3);
								break;
							case 4 :
								Intent myIntent4 = new Intent(List_promo.this, Liste_eleves.class);
								myIntent4.putExtra("promo", "A5");
								List_promo.this.startActivity(myIntent4);
								break;
								
							case 5 :
								Intent myIntent5 = new Intent(List_promo.this, Liste_eleves.class);
								myIntent5.putExtra("promo", "Tuteurs");
								List_promo.this.startActivity(myIntent5);
								break;
								
							case 6 :
								Intent myIntent6 = new Intent(List_promo.this, Liste_eleves.class);
								myIntent6.putExtra("promo", "Guest");
								List_promo.this.startActivity(myIntent6);
								break;
								
							}
				}
			});
	}
	
	private class CAsyncTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>> {
    	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		@Override
        protected ArrayList<HashMap<String, Object>> doInBackground (Void... args) {
        	String result = "";
    		//the year data to send
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		InputStream is = null;
    		
    		//http post
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requetes_liste_promo.php");
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
            		HashMap<String, Object> map;
                    map = new HashMap<String, Object>();
                   json_data = jArray.getJSONObject(i);
                   map.put("nom",json_data.getString("nom"));
                   listItem.add(map);
               }
            	return listItem;
            }
            catch(JSONException e1){
            	//Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
            	Log.e("log_tag e1", "Error "+e1.toString());
            } catch (ParseException e1) {
            	//Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
            	Log.e("log_tag e1", "Error "+e1.toString());
      	}
        	return null;
            } // doInBackground ();
            
            @Override
            protected void onPostExecute (ArrayList<HashMap<String, Object>> _list) {
            	if(_list == null)
            	{
            		Log.e("log_tag_null", "Error ");
            	}
            	else
            	{
            		SimpleAdapter adapter = new SimpleAdapter(List_promo.this, _list, R.layout.promos_listing, new String[] {"nom"}, new int[] {R.id.nom_promo});
            		adapter.setViewBinder(new MyViewBinder());
            		lv.setAdapter(adapter);
            	}
            } // onPostExecute ();

    	
            
        } // CAsyncTask ();
}

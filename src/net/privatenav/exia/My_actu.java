package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class My_actu extends ListFragment {
	
	private String id = null; 
	private String prenom = null;
	private String nom = null;
	private String pseudo = null;
	private String photo = null;
	private ListView lv;
	private String idfix = null;


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View V = inflater.inflate(R.layout.my_actu, container, false);
		 
		 try {
		    id = new Pp_menu().getid();
		    prenom = new Pp_menu().getprenom();
		    nom = new Pp_menu().getnom();
		    photo = new Pp_menu().getphoto();
		    pseudo = new Pp_menu().getpseudo();
		 	Log.e("loh_tag", id);
			}
		 catch (Exception e) 
			{
			        Log.e("log_tag", "Error  "+id);
			}
		 
		 try {
	    	 CAsyncTask task = new CAsyncTask ();
	         task.execute(id);
	     } catch (Exception e) {
		        Log.e("log_tag_lol", "Error");
		}
		 
		return V;
    }
	
	@Override
	public void onResume() {
		 try {
	    	 CAsyncTask task = new CAsyncTask ();
	         task.execute(id);
	     } catch (Exception e) {
		        Log.e("log_tag_lol", "Error");
		}
		super.onResume();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 display();
	}
	
	private void display()
	{
		final
		Context context = getActivity().getApplicationContext();
		lv = (ListView) getView().findViewById(android.R.id.list);
	     lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(arg2);	        		
	        		//Toast.makeText(Actu.this, "L'item '" + o.get("idfix").toString() + "' was clicked.", Toast.LENGTH_SHORT).show();
	        		Log.e("log_actuclick", "j'ai cliqué ça marche");
	        		
	        		Intent myIntent4 = new Intent(My_actu.this.getActivity().getApplicationContext(), commentaires.class);
	  	    	 	myIntent4.putExtra("idfix", o.get("idfix").toString());
	  	    	 	myIntent4.putExtra("nom", nom.toString());
	  	    	 	myIntent4.putExtra("prenom", prenom.toString());
	  	    	 	myIntent4.putExtra("photo_p", photo.toString());
	  	    	 	myIntent4.putExtra("id", id.toString());
	  	    	 	myIntent4.putExtra("pseudo", pseudo.toString());
	  	    	 	My_actu.this.startActivity(myIntent4);
				    }
			});
	     
	     lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(My_actu.this.getActivity());
				builder.setCancelable(true);
				builder.setItems(R.array.delete, new DialogInterface.OnClickListener(){
		               public void onClick(DialogInterface dialog, int which) {
		            	   switch (which) {
						case 0:
							AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
					        builder1.setMessage("Supprimer la publication")
					               .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
					                   public void onClick(DialogInterface dialog, int id) {
					                	   @SuppressWarnings("unchecked")
					   						HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(arg2);	  
					   						CAsyncTask2 task2 = new CAsyncTask2 ();
					   						task2.execute(o.get("idfix").toString());
					   						regenerer();
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
				
			    Log.e("log_tag", "J'ai cliqué !!!");
				return false;
			}
		});
			
	}
	
	private void regenerer()
	{
		CAsyncTask task = new CAsyncTask ();
        task.execute(id);
	}

	
	private class CAsyncTask extends AsyncTask<String, Void, ArrayList<HashMap<String, Object>>> {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		Context context = getActivity().getApplicationContext();
        protected ArrayList<HashMap<String, Object>> doInBackground (String... arg) {
        	String result = "";
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	nameValuePairs.add(new BasicNameValuePair("id",arg[0]));
    		InputStream is = null;
    		//http post
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_mon_actu.php");
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
                  
                   map.put("message", json_data.getString("message"));
                   map.put("date", json_data.getString("date"));
                   map.put("idfix", json_data.getString("message_id"));
                   map.put("com", json_data.getString("nbcommentaires"));
    	        	
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
        	if(_list == null)
        	{
        		Log.e("log_tag_null", "Error ");
        	}
        	else
        	{
        		SimpleAdapter adapter = new SimpleAdapter(context, _list, R.layout.derniere_actu, new String[] {"message", "date", "com"}, new int[] {R.id.message_moi, R.id.the_date_moi, R.id.button1});
        	    adapter.setViewBinder(new MyViewBinder());
        	    lv.setAdapter(adapter);
				
        	}
        } // onPostExecute ();
        
    } // CAsyncTask ();
	
	private class CAsyncTask2 extends AsyncTask<String, Void, Void> {
	    
	    protected Void doInBackground (String... args) {
			String result = "";
			//the year data to send
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("message_id",args[0]));
			InputStream is = null;
			ArrayList resultats = new ArrayList();
			ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
			List r = new ArrayList();
			//http post
			try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/delete_message.php");
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
	           }   
	        	
	        }
	        catch(JSONException e1){
	        //	Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
	        	Log.e("TAG_json", e1.toString());
	        } catch (ParseException e1) {
	        	//Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
	        	Log.e("TAG_json", e1.toString());
	  	}
	        
	        return null;
	    } // doInBackground ();
	    
	    protected void onPostExecute (final Void unused) {
	        Toast.makeText (My_actu.this.getActivity().getApplicationContext(), "Actualité supprimée avec succès", Toast.LENGTH_SHORT).show ();
	    } // onPostExecute ();
	    
	} // CAsyncTask ();
}

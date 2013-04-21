package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class commentaires extends ListActivity{
	
	private static String idfix = null;
	private String nom = null;
	private String prenom = null;
	private String pseudo = null;
	private String photo_p = null;
	private String id = null;
	private ListView lv1;
	private ProgressBar mProgressBar;
	
	@Override
	protected void onResume() {
		CAsyncTask1 task1 = new CAsyncTask1();
		 task1.execute(idfix);
		super.onResume();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    	    idfix = extras.getString("idfix");
    	    nom = extras.getString("nom");
    	    prenom =extras.getString("prenom");
    	    pseudo =extras.getString("pseudo");
    	    photo_p = extras.getString("photo_p");
    	    id =extras.getString("id");
    	}
		
		lv1 = (ListView) findViewById(android.R.id.list);
		
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		mProgressBar.setVisibility(View.VISIBLE);
		CAsyncTask1 task1 = new CAsyncTask1();
		task1.execute(idfix);
		 
		 Button _commenter = (Button) findViewById(R.id.commenter);
		 _commenter.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				
		        EditText _commentaire = (EditText)findViewById(R.id.com);
		        if(_commentaire.getText().length() < 1)
		        {
		        	Toast.makeText(commentaires.this,"Merci de rédiger un message réglementaire",Toast.LENGTH_SHORT).show();
		        }
		        else
		        {
		        	mProgressBar.setVisibility(View.VISIBLE);
			        EditText _com = (EditText) findViewById(R.id.com);
			        CAsyncTask task = new CAsyncTask ();
			        task.execute (id, nom, prenom, pseudo, photo_p, _com.getText().toString(),idfix);
			        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			        _com.setText(null);
			        CAsyncTask1 task1 = new CAsyncTask1();
					task1.execute(idfix);
		        }
			}
		});
	}
	
	private class CAsyncTask1 extends AsyncTask<String, Void, ArrayList<HashMap<String, Object>>> {
	    protected ArrayList<HashMap<String, Object>> doInBackground (String... arg) {
	    	String result = "";
	    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			InputStream is = null;
			ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
			//http post
			try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_commentaires.php");
			        nameValuePairs.add(new BasicNameValuePair("id",arg[0]));
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
	               map.put("photo", json_data.getString("photo"));
	               map.put("idfix", json_data.getString("comment_id"));
	               //idfix = json_data.getString("comment_id");
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
	    	if(_list != null)
	    	{
	    		mProgressBar.setVisibility(View.INVISIBLE);
	    		SimpleAdapter adapter = new SimpleAdapter(commentaires.this, _list, R.layout.comment_layout, new String[] {"name", "message", "photo"}, new int[] {R.id.allpseudo, R.id.message_comment, R.id.thumb_photo});
		    	adapter.setViewBinder(new MyViewBinder());
		    	lv1.setAdapter(adapter);
	    	}
	    	else
	    	{
	    		mProgressBar.setVisibility(View.INVISIBLE);
	    		String message = "Aucun commentaire recensé, inaugurez donc.";
	    		Toast.makeText (commentaires.this, message, Toast.LENGTH_SHORT).show ();
	    	}
	    	
	    } // onPostExecute ();
	    
	} // CAsyncTask ();

private class CAsyncTask extends AsyncTask<String, Void, Void> {
        
        protected Void doInBackground (String... args) {       	
    		String result = "";
    		//the year data to send
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		nameValuePairs.add(new BasicNameValuePair("id",args[0]));
    		nameValuePairs.add(new BasicNameValuePair("nom",args[1]));
    		nameValuePairs.add(new BasicNameValuePair("prenom",args[2]));
    		nameValuePairs.add(new BasicNameValuePair("pseudo",args[3]));
    		nameValuePairs.add(new BasicNameValuePair("photo",args[4]));
    		nameValuePairs.add(new BasicNameValuePair("mess",args[5]));
    		nameValuePairs.add(new BasicNameValuePair("idfix",args[6]));
    		Log.e("Log idfix com", args[6].toString());
    		InputStream is = null;
    		ArrayList resultats = new ArrayList();
    		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    		List r = new ArrayList();
    		//http post
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_add_commentaires.php");
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
            String message = "Publication ajoutée avec succès.";
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText (commentaires.this.getApplicationContext(), message, Toast.LENGTH_SHORT).show ();
        } // onPostExecute ();
        
	}
}

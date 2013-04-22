package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Connect extends Activity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        
        Button btn_co = (Button) findViewById(R.id.button1);
        btn_co.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				EditText text_pseudo = (EditText)findViewById(R.id.editText2);
				EditText text_mdp = (EditText)findViewById(R.id.editText1);
				ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar1);
				progress.setVisibility(View.VISIBLE);
		    	CAsyncTask task = new CAsyncTask ();
		        task.execute (text_pseudo.getText().toString(), text_mdp.getText().toString());
			}
			
		});
        Button btn_mdp = (Button) findViewById(R.id.button2);
        btn_mdp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent myIntent = new Intent(Connect.this, Forget_mdp.class);
  	    	 	Connect.this.startActivity(myIntent);
			}
		});
        
        Button btn_inscription = (Button)findViewById(R.id.button3);
        btn_inscription.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent myIntent = new Intent(Connect.this, Inscription.class);
  	    	 	Connect.this.startActivity(myIntent);
			}
		});
        
    }
    
    
private class CAsyncTask extends AsyncTask<String, Void, String> {
	
	@Override
	protected void onPreExecute() {
		TextView txt = (TextView) findViewById(R.id.textView3);
		txt.setText("Connexion en cours...");
		super.onPreExecute();
	}
        
        protected String doInBackground (String... pars) {
        	String result = "";
    		//the year data to send
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		nameValuePairs.add(new BasicNameValuePair("pseudo",pars[0]));
    		nameValuePairs.add(new BasicNameValuePair("mdp",pars[1]));
    		InputStream is = null;
    		//http post
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requetes_co.php");
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
    		        while ((line = reader.readLine()) != null) 
    		        {
    		           sb.append(line + "\n");
    		        }
    		        is.close();
    		        result=sb.toString();
    		}
    		catch(Exception e)
    		{
    		   Log.e("log_tag", "Error converting result "+e.toString());
    		}
    		 
    		//parse json data
    		try{
        		JSONArray jArray = new JSONArray(result);
            	JSONObject json_data=null;
            	for(int i=0;i<jArray.length();i++)
            	{
                   json_data = jArray.getJSONObject(i);
       				Intent myIntent = new Intent(Connect.this, Pp_menu.class);
       				myIntent.putExtra("prenom", json_data.getString("prenom").toString());
      	    	 	myIntent.putExtra("nom", json_data.getString("nom").toString());
      	    	 	myIntent.putExtra("pseudo", json_data.getString("pseudo").toString());
      	    	 	myIntent.putExtra("photo", json_data.getString("photo").toString());
      	    	 	myIntent.putExtra("promo", json_data.getString("promo").toString());
      	    	 	myIntent.putExtra("idé", json_data.getString("id").toString());
      	    	 	Connect.this.startActivity(myIntent);
                   Log.e("log_tag", json_data.getString("prenom").toString());
                   Log.e("log_tag", json_data.getString("nom").toString());
                   Log.e("log_tag", json_data.getString("pseudo").toString());
      	    	 	String global = json_data.getString("pseudo").toString();
      	    	 	String _re = "vrai";
      	    	 	return _re;
               }
            }
            catch(JSONException e1){
            	//TextView txt = (TextView) findViewById(R.id.textView3);
    			//txt.setText("Mot de passe ou pseudoynme incorrect");
            	//txt.setText(e1.toString());
            	Log.e("log_tag", "Error converting result "+e1.toString());
            } catch (ParseException e1) {
            	//Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
            	Log.e("log_tag", "Error converting result "+e1.toString());
      	}
    		String _re = "faux";             
            return _re;
        } // doInBackground ();
        
        protected void onPostExecute (String retour) {
        	if (retour == "vrai")
        	{
        		ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar1);
        		progress.setVisibility(View.INVISIBLE);
        		TextView txt = (TextView) findViewById(R.id.textView3);
        		txt.setText("Connexion approuvée");
        	}
        	else
        	{
        		ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar1);
            	progress.setVisibility(View.INVISIBLE);
            	TextView txt = (TextView) findViewById(R.id.textView3);
    			txt.setText("Pseudo ou mot de passe incorrect :(");
        	}
           // Toast.makeText (activity, message, Toast.LENGTH_SHORT).show ();
        } // onPostExecute ();
        
    } // CAsyncTask ();

}

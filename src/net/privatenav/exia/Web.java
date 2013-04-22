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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

public class Web extends Fragment {
	
	private View V = null;
	private String id = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 V =  inflater.inflate(R.layout.web_layout, container, false);
		 
		 try {
			 id = new Pp_menu().getid();
		} catch (Exception e) {
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
	
	
	private class CAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
		Context context = getActivity().getApplicationContext();
		
        protected ArrayList<String> doInBackground (String... arg) {
        	String result = "";
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	nameValuePairs.add(new BasicNameValuePair("id",arg[0]));
    		InputStream is = null;
    		//http post
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_competences.php");
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
	        	ArrayList<String> al = new ArrayList<String>();
	        	for(int i=0;i<jArray.length();i++)
	        	{
	               json_data = jArray.getJSONObject(i); 
	               al.add(json_data.getString("html").toString());
	               al.add(json_data.getString("js").toString());
	               al.add(json_data.getString("php").toString());
	           }   
	        return al;
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
        
        protected void onPostExecute (ArrayList<String> _list) {
        	if(_list == null)
        	{
        		Log.e("log_tag_null", "Error ");
        	}
        	else
        	{
        		RatingBar _html = (RatingBar)V.findViewById(R.id.ratingBar1);
        		Float html = Float.parseFloat(_list.get(1));
        		_html.setRating(html);
        		
        		RatingBar _js = (RatingBar)V.findViewById(R.id.ratingBar1);
        		Float js = Float.parseFloat(_list.get(2));
        		_js.setRating(js);
        		
        		RatingBar _php = (RatingBar)V.findViewById(R.id.ratingBar1);
        		Float php = Float.parseFloat(_list.get(3));
        		_php.setRating(php);
        		
        	}
        } // onPostExecute ();
        
	}

}

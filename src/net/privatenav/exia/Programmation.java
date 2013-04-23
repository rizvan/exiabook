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

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class Programmation extends Fragment{
	
	private View V = null;
	private String id = null;
	private float cNB =(Float) null;
	private float cppNB =(Float) null;
	private float netNB =(Float) null;
	private float javaNB =(Float) null;
	private float bddNB =(Float) null;
	private float mobileNB =(Float) null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		V= inflater.inflate(R.layout.programmation_layout, container, false);
		
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
    		ArrayList<String> al = new ArrayList<String>();
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
	        	
	        	for(int i=0;i<jArray.length();i++)
	        	{
	               json_data = jArray.getJSONObject(i); 
	               al.add(json_data.getString("c").toString());
	               al.add(json_data.getString("c++").toString());
	               al.add(json_data.getString(".net").toString());
	               al.add(json_data.getString("java").toString());
	               al.add(json_data.getString("bdd").toString());
	               al.add(json_data.getString("mobile").toString());
	               cNB = Float.parseFloat(json_data.getString("c").toString());
	               cppNB = Float.parseFloat(json_data.getString("c++").toString());
	               netNB = Float.parseFloat(json_data.getString(".net").toString());
	               javaNB =Float.parseFloat(json_data.getString("java").toString());
	               bddNB = Float.parseFloat(json_data.getString("bdd").toString());
	               mobileNB = Float.parseFloat(json_data.getString("mobile").toString());
	           }   
	        
	        }
	        catch(JSONException e1){
	        	//Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
	        	Log.e("TAG_json", e1.toString());
	        } catch (ParseException e1) {
	        	//Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
	        	Log.e("TAG_json", e1.toString());
	  	}
            
    		return al;
        } 
        
        protected void onPostExecute (ArrayList<String> _list) {
        	if(_list == null)
        	{
        		Log.e("log_tag_null", "Error ");
        	}
        	else
        	{
        		RatingBar _c = (RatingBar)V.findViewById(R.id.ratingBar1);
        		Float c = Float.parseFloat(_list.get(0));
        		_c.setRating((float)c);
        		_c.setVisibility(V.VISIBLE);
        		
        		TextView _ctext = (TextView)V.findViewById(R.id.textView2);
        		_ctext.setVisibility(V.VISIBLE);
        		
        		ImageView _cimg = (ImageView)V.findViewById(R.id.imageView1);
        		_cimg.setVisibility(V.VISIBLE);
        		
        		RatingBar _cpp = (RatingBar)V.findViewById(R.id.RatingBar01);
        		Float cpp = Float.parseFloat(_list.get(1));
        		_cpp.setRating((float)cpp);
        		_cpp.setVisibility(V.VISIBLE);
        		
        		TextView _cpptext = (TextView)V.findViewById(R.id.textView3);
        		_cpptext.setVisibility(V.VISIBLE);
        		
        		ImageView _cppimg = (ImageView)V.findViewById(R.id.imageView2);
        		_cppimg.setVisibility(V.VISIBLE);
        		
        		RatingBar _net = (RatingBar)V.findViewById(R.id.RatingBar02);
        		Float net = Float.parseFloat(_list.get(2));
        		_net.setRating((float)net);
        		_net.setVisibility(V.VISIBLE);
        		
        		TextView _nettext = (TextView)V.findViewById(R.id.textView4);
        		_nettext.setVisibility(V.VISIBLE);
        		
        		ImageView _netimg = (ImageView)V.findViewById(R.id.imageView3);
        		_netimg.setVisibility(V.VISIBLE);
        		
        		RatingBar _java = (RatingBar)V.findViewById(R.id.RatingBar03);
        		Float java = Float.parseFloat(_list.get(2));
        		_java.setRating((float)java);
        		_java.setVisibility(V.VISIBLE);
        		
        		TextView _javatext = (TextView)V.findViewById(R.id.textView5);
        		_javatext.setVisibility(V.VISIBLE);
        		
        		ImageView _javaimg = (ImageView)V.findViewById(R.id.imageView4);
        		_javaimg.setVisibility(V.VISIBLE);
        		
        		RatingBar _bdd = (RatingBar)V.findViewById(R.id.RatingBar04);
        		Float bdd = Float.parseFloat(_list.get(2));
        		_bdd.setRating((float)bdd);
        		_bdd.setVisibility(V.VISIBLE);
        		
        		TextView _bddtext = (TextView)V.findViewById(R.id.textView6);
        		_bddtext.setVisibility(V.VISIBLE);
        		
        		ImageView _bddimg = (ImageView)V.findViewById(R.id.imageView5);
        		_bddimg.setVisibility(V.VISIBLE);
        		
        		RatingBar _mobile = (RatingBar)V.findViewById(R.id.RatingBar05);
        		Float mobile = Float.parseFloat(_list.get(2));
        		_mobile.setRating((float)mobile);
        		_mobile.setVisibility(V.VISIBLE);
        		
        		TextView _mobiletext = (TextView)V.findViewById(R.id.textView7);
        		_mobiletext.setVisibility(V.VISIBLE);
        		
        		ImageView _mobileimg = (ImageView)V.findViewById(R.id.imageView6);
        		_mobileimg.setVisibility(V.VISIBLE);
        		
        		ProgressBar _prog = (ProgressBar)V.findViewById(R.id.progressBar1);
        		_prog.setVisibility(V.INVISIBLE);
        	}
        }
        
	}
}

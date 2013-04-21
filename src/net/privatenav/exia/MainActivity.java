package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.app.ListActivity;

public class MainActivity extends ListActivity {
	
	private void test()
	{
		String result = "";
		//the year data to send
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("year","1"));
		InputStream is = null;
		ArrayList resultats = new ArrayList();
		List r = new ArrayList();
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requetes.php");
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
               r.add(json_data.getString("nom"));
           }
           setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, r));
        }
        catch(JSONException e1){
        	Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
        } catch (ParseException e1) {
        	Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
  	}
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test();
    }

}

package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Publishing extends Activity {
	
	private String nom = null;
	private String prenom = null;
	private String pseudo = null;
	private String photo_p = null;
	private String mess = null;
	private String id = null;
	private Publishing activity;
	private ProgressBar mProgressBar;
	private String youtubeimg = null;
	private String youtubetitre = null;
	private String youtubeurl = null;
	
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publishing);
		 Bundle extras = getIntent().getExtras();
	    	if (extras != null) {
	    	    nom = extras.getString("nom");
	    	    prenom = extras.getString("prenom");
	    	    pseudo = extras.getString("pseudo");
	    	    photo_p = extras.getString("photo");
	    	    id = extras.getString("idé");
	    	}
	    	activity = this;
	    	
	    	new DownloadImageTask((ImageView) findViewById(R.id.image_profil)).execute(photo_p);
		
	    	SimpleDateFormat sdf = new SimpleDateFormat("HH : mm : ss");
	    	String currentDateandTime = sdf.format(new Date());
	    	
	    	TextView _date = (TextView) findViewById(R.id.datetime);
	    	_date.setText("- à " +currentDateandTime);
	    	
	    	final EditText _message = (EditText) findViewById(R.id.editText1);
	    	final TextView _count = (TextView)findViewById(R.id.compteur);
			_message.addTextChangedListener(new TextWatcher() {
				
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				public void afterTextChanged(Editable arg0) {
					_count.setText(""+(500-_message.getText().length()));
				}
			});
	    	
	    	/*Button _btnYoutube = (Button) findViewById(R.id.youtube);
	    	_btnYoutube.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					
					//
					final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
				    final EditText input = new EditText(activity);
				    alert.setView(input);
				    alert.setPositiveButton("Partager", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				            try {
				            	SimpleYouTubeHelper _youtube = new SimpleYouTubeHelper();
					            new DownloadImageTask((ImageView) findViewById(R.id.imgPreview)).execute(_youtube.getImageUrlQuietly(input.getText().toString()));
					            youtubeimg = _youtube.getImageUrlQuietly(input.getText().toString());
					            
					            ImageView _imgPrev = (ImageView) findViewById(R.id.imgPreview);
					            _imgPrev.setVisibility(View.VISIBLE);
					            
					            youtubeurl = input.getText().toString();
					            
					            CAsyncTask1 task1 = new CAsyncTask1();
					            task1.execute(input.getText().toString());
					            
					            Button _watch = (Button) findViewById(R.id.button1);
					            _watch.setOnClickListener(new OnClickListener() {
									
									public void onClick(View v) {
										String idVideo = input.getText().toString();
										
										Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + idVideo));
										startActivity(intent);
									}
								});
					            _watch.setVisibility(View.VISIBLE);
					            
							} catch (Exception e) {
								Log.e("Tag_youtube", e.toString());
							}
				        }
				    });

				    alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				            dialog.cancel();
				        }
				    });
				    alert.show();  
				}
			});
	    	*/
	    	Button _butValider = (Button) findViewById(R.id.valider);
	    	_butValider.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					EditText _Contenu = (EditText)findViewById(R.id.editText1);
			        if(_Contenu.getText().length() < 2)
			        {
			        	String message = "Merci de créer un message réglementaire";
				        Toast.makeText (Publishing.this.getApplicationContext(), message, Toast.LENGTH_LONG).show ();
			        }
			        else
			        {
			        	String message = "Merci de bien vouloir patienter...";
				        Toast.makeText (Publishing.this.getApplicationContext(), message, Toast.LENGTH_LONG).show ();
						mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
				        mProgressBar.setVisibility(View.VISIBLE);
				        
				        EditText _message = (EditText) findViewById(R.id.editText1);
						mess = _message.getText().toString();
						
						CAsyncTask task = new CAsyncTask ();
						CAsyncTask2 task2 = new CAsyncTask2 ();
						if((youtubeimg != null))
						{
							task2.execute (id, nom, prenom, pseudo, photo_p,mess, youtubeimg, youtubetitre, youtubeurl);
							Log.e("Log execute2", "2");
						}
						else
						{
							task.execute (id, nom, prenom, pseudo, photo_p,mess);
							Log.e("Log execute1", "1");
						}
			        }
				}
			});
	    	
	    	Button _butAnnuler = (Button) findViewById(R.id.annuler);
	    	_butAnnuler.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Publishing.this.finish();
				}
			});
	    	
	    	
	}

private class CAsyncTask extends AsyncTask<String, Void, Void> {
        
        protected Void doInBackground (String... args) {
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        	String currentDate = sdf.format(new Date());
        	
        	
    		String result = "";
    		//the year data to send
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		nameValuePairs.add(new BasicNameValuePair("id",args[0]));
    		nameValuePairs.add(new BasicNameValuePair("nom",args[1]));
    		nameValuePairs.add(new BasicNameValuePair("prenom",args[2]));
    		nameValuePairs.add(new BasicNameValuePair("pseudo",args[3]));
    		nameValuePairs.add(new BasicNameValuePair("photo",args[4]));
    		nameValuePairs.add(new BasicNameValuePair("mess",args[5]));
    		nameValuePairs.add(new BasicNameValuePair("date",currentDate.toString()));
    		InputStream is = null;
    		ArrayList resultats = new ArrayList();
    		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    		List r = new ArrayList();
    		//http post
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_add_messages.php");
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
            Toast.makeText (activity, message, Toast.LENGTH_SHORT).show ();
            Publishing.this.finish();
        } // onPostExecute ();
        
    } // CAsyncTask ();
//pour upload un lien youtube

private class CAsyncTask2 extends AsyncTask<String, Void, Void> {
    
    protected Void doInBackground (String... args) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
    	String currentDate = sdf.format(new Date());
    	
    	
		String result = "";
		//the year data to send
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id",args[0]));
		nameValuePairs.add(new BasicNameValuePair("nom",args[1]));
		nameValuePairs.add(new BasicNameValuePair("prenom",args[2]));
		nameValuePairs.add(new BasicNameValuePair("pseudo",args[3]));
		nameValuePairs.add(new BasicNameValuePair("photo",args[4]));
		nameValuePairs.add(new BasicNameValuePair("mess",args[5]));
		nameValuePairs.add(new BasicNameValuePair("date",currentDate.toString()));
		nameValuePairs.add(new BasicNameValuePair("youtubeimg",args[6]));
		nameValuePairs.add(new BasicNameValuePair("youtubetitre",args[7]));
		nameValuePairs.add(new BasicNameValuePair("youtubeurl",args[8]));
		InputStream is = null;
		ArrayList resultats = new ArrayList();
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		List r = new ArrayList();
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_add_messages_youtube.php");
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
        Toast.makeText (activity, message, Toast.LENGTH_SHORT).show ();
    } // onPostExecute ();
    
} // CAsyncTask ();

//pour recuperer les inos youtube

private class CAsyncTask1 extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... arg0) {
		SimpleYouTubeHelper _youtube = new SimpleYouTubeHelper();
		String titre = null;
		titre = _youtube.getTitleQuietly(arg0[0].toString());
		return titre;
	}
	
	@Override
		protected void onPostExecute(String result) {
		TextView _titlePrev = (TextView) findViewById(R.id.titrePreview);
        _titlePrev.setText(result);
        youtubetitre = result;
        Log.e("Tag_youtube", result);
        _titlePrev.setVisibility(View.VISIBLE);
			super.onPostExecute(result);
		}

}


}

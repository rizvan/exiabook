package net.privatenav.exia;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Choose_photo extends Activity {
	
	private String nom = null;
	private String prenom = null;
	private String pseudo = null;
	private String mail = null;
	private String mdp = null;
	private String promo = null;
	private String photo = null;
	String result = "";

	private static final int SELECT_PICTURE = 2;
    String selectedPath = "";
    InputStream inputStream;
    private ProgressBar mProgressBar;
    private TextView _mytxt;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        
        /*
         * On recupere les infos precedemment remplies 
         * OUF c'est bientôt fini !
         * 
         */
        
        Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    	    nom = extras.getString("nom");
    	    prenom = extras.getString("prenom");
    	    pseudo = extras.getString("pseudo");
    	    mail = extras.getString("mail");
    	    mdp = extras.getString("mdp");
    	    promo = extras.getString("promo");
    	}
    	
        ImageButton _imgbtn = (ImageButton)findViewById(R.id.imageButton1);
        _imgbtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				 openGalleryAudio();
				Button _btn = (Button)findViewById(R.id.button1);
				_btn.setEnabled(true);
			}
		});
       
       
        
        Button _btn = (Button)findViewById(R.id.button1);
        _btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
				_mytxt = (TextView)findViewById(R.id.textView1);
				mProgressBar.setVisibility(View.VISIBLE);
				CAsyncTask task = new CAsyncTask();
				task.execute(_mytxt.getText().toString());
			}
		});
        
        Button _go = (Button)findViewById(R.id.Button01);
        _go.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				CAsyncTask1 task1 = new CAsyncTask1();
				task1.execute(pseudo, mdp, nom, prenom, mail, promo, photo);
			}
		});
    }

    public void openGalleryAudio(){
   	 
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
       }
     
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
     
            if (resultCode == RESULT_OK) {
     
                if (requestCode == SELECT_PICTURE)
                {
                    System.out.println("SELECT_PICTURE");
                    Uri selectedImageUri = data.getData();
                    selectedPath = getPath(selectedImageUri);
                    
                    System.out.println("SELECT_PICTURE Path : " + selectedPath);
                    TextView _path = (TextView)findViewById(R.id.textView1);
                    _path.setText(selectedPath);
                    
                    ImageView _img = (ImageView)findViewById(R.id.imageView1);
                    Bitmap bmp = BitmapFactory.decodeFile((String)_path.getText());
                   _img.setImageBitmap( bmp);
                   
                }
     
            }
        }
     
        public String getPath(Uri uri) {
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
     
        
        
        private class CAsyncTask extends AsyncTask<String, Void, String> {
        	
        	@Override
        	protected void onPreExecute() {
        	TextView txt = (TextView) findViewById(R.id.textView1);
        		txt.setText("Upload en cours...");
        	super.onPreExecute();
        	}
                
                protected String doInBackground (String... pars) {
                	final String _path = pars[0];
                    HttpURLConnection conn = null;
                    DataOutputStream dos = null;
                    DataInputStream inStream = null;
                    String existingFileName = _path;
                    String lineEnd = "\r\n";
                    String twoHyphens = "--";
                    String boundary =  "*****";
                    int bytesRead, bytesAvailable, bufferSize;
                    byte[] buffer;
                    int maxBufferSize = 1*4096;
                    String responseFromServer = "";
                    String urlString = "http://185.14.185.122/exia/upload.php";
                    try
                    {
                     //------------------ CLIENT REQUEST
                     FileInputStream fileInputStream = new FileInputStream(new File(existingFileName) );
                     // open a URL connection to the Servlet
                     URL url = new URL(urlString);
                     // Open a HTTP connection to the URL
                     conn = (HttpURLConnection) url.openConnection();
                     // Allow Inputs
                     conn.setDoInput(true);
                     // Allow Outputs
                     conn.setDoOutput(true);
                     // Don't use a cached copy.
                     conn.setUseCaches(false);
                     // Use a post method.
                     conn.setRequestMethod("POST");
                     conn.setRequestProperty("Connection", "Keep-Alive");
                     conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
                     dos = new DataOutputStream( conn.getOutputStream() );
                     dos.writeBytes(twoHyphens + boundary + lineEnd);
                     dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + existingFileName + "\"" + lineEnd);
                     dos.writeBytes(lineEnd);
                     // create a buffer of maximum size
                     bytesAvailable = fileInputStream.available();
                     bufferSize = Math.min(bytesAvailable, maxBufferSize);
                     buffer = new byte[bufferSize];
                     // read file and write it into form...
                     bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                     while (bytesRead > 0)
                     {
                      dos.write(buffer, 0, bufferSize);
                      bytesAvailable = fileInputStream.available();
                      bufferSize = Math.min(bytesAvailable, maxBufferSize);
                      bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                      
                     }
                     // send multipart form data necesssary after file data...
                     dos.writeBytes(lineEnd);
                     dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                     // close streams
                     Log.e("Debug","File is written");
                     fileInputStream.close();
                     dos.flush();
                     dos.close();
                    
                    }
                    catch (MalformedURLException ex)
                    {
                         Log.e("Debug", "error: " + ex.getMessage(), ex);
                    }
                    catch (IOException ioe)
                    {
                         Log.e("Debug", "error: " + ioe.getMessage(), ioe);
                    }
                    //------------------ read the SERVER RESPONSE
                    try {
                          inStream = new DataInputStream ( conn.getInputStream() );
                          String str;
                         
                          while (( str = inStream.readLine()) != null)
                          {
                               Log.e("Debug","Server Response "+str);
                               photo = "http://185.14.185.122/exia/uploads/"+str;
                               
                          }
                          inStream.close();
                          

                    }
                    catch (IOException ioex){
                         Log.e("Debug", "error: " + ioex.getMessage(), ioex);
                         return "fail";
                    }
					return null;
                } // doInBackground ();
                
                protected void onPostExecute (String retour) {
                	if (retour == "fail")
                	{
                		TextView txt = (TextView) findViewById(R.id.textView1);
                		txt.setText("Echec lors de l'upload, réessayez.");
                		mProgressBar.setVisibility(View.INVISIBLE);
                	}
                	else
                	{
                		mProgressBar.setVisibility(View.INVISIBLE);
                		Button _go = (Button)findViewById(R.id.Button01);
                		_go.setTextColor(Color.WHITE);
                		_go.setEnabled(true);
                		Button _upload = (Button) findViewById(R.id.button1);
                		_upload.setVisibility(View.INVISIBLE);
                		TextView txt = (TextView) findViewById(R.id.textView1);
                		txt.setText("Photo uploadée, merci de créer votre compte.");
                	}
                   // Toast.makeText (activity, message, Toast.LENGTH_SHORT).show ();
                } // onPostExecute ();
                
            } // CAsyncTask ();
        
        
        private class CAsyncTask1 extends AsyncTask<String, Void, String> {
        	
        	@Override
        	protected void onPreExecute() {
        		TextView txt = (TextView) findViewById(R.id.textView1);
        		txt.setText("Inscription en cours...");
        	super.onPreExecute();
        	}

			@Override
			protected String doInBackground(String... arg0) {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("pseudo",arg0[0]));
				nameValuePairs.add(new BasicNameValuePair("mdp",arg0[1]));
				nameValuePairs.add(new BasicNameValuePair("nom",arg0[2]));
				nameValuePairs.add(new BasicNameValuePair("prenom",arg0[3]));
				nameValuePairs.add(new BasicNameValuePair("mail",arg0[4]));
				nameValuePairs.add(new BasicNameValuePair("promo",arg0[5]));
				nameValuePairs.add(new BasicNameValuePair("photo",arg0[6]));
				
				Log.e("log_pseudo", arg0[0].toString());
				Log.e("log_photo", arg0[6].toString());
				InputStream is = null;
				//http post
				try{
					
				        HttpClient httpclient = new DefaultHttpClient();
				        HttpPost httppost = new HttpPost("http://185.14.185.122/exia/requete_ajout.php");
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
		               //coonnexion reussie            
		              /*Intent myIntent = new Intent(Choose_photo.this, Connect.class);
		              myIntent.putExtra("pseudo", pseudo);
		  	    	  Choose_photo.this.startActivity(myIntent);*/
		              if(jArray.length() != 0)
		              {
		            	  return null;
		              }
		           }
		        }
		        catch(JSONException e1){
		        } catch (ParseException e1) {
		        }
				
			
				return "ok";
			}
			
			@Override
				protected void onPostExecute(String result) {
					if(result =="ok")
					{
						TextView txt = (TextView) findViewById(R.id.textView1);
		        		txt.setText("Inscription effectuée.");
					}
					else
					{
						TextView txt = (TextView) findViewById(R.id.textView1);
		        		txt.setText("L'inscription a echoué.");
					}
					super.onPostExecute(result);
				}
			
		}
}


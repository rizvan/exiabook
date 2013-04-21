package net.privatenav.exia;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Select_promo extends Activity {
	private String nom = null;
	private String prenom = null;
	private String pseudo = null;
	private String mail = null;
	private String mdp = null;
	private String promo = null;
	
	private RadioButton radioSexButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_promo);
        
        Bundle extras = getIntent().getExtras();
    	if (extras != null) {
    	    nom = extras.getString("nom");
    	    prenom = extras.getString("prenom");
    	    pseudo = extras.getString("pseudo");
    	    mail = extras.getString("mail");
    	    mdp = extras.getString("mdp");
    	}
    	
    	
			

        Button _btn = (Button)findViewById(R.id.button1);
        _btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				RadioGroup _temp = (RadioGroup)findViewById(R.id.radioGroup1);
		    	
				int selected = _temp.getCheckedRadioButtonId() ;
				radioSexButton = (RadioButton) findViewById(selected);
				 promo = radioSexButton.getText().toString();
				/*Toast.makeText(Select_promo.this,radioSexButton.getText(), Toast.LENGTH_SHORT).show();*/
			
				
				Intent myIntent = new Intent(Select_promo.this, Choose_photo.class);
				myIntent.putExtra("prenom", prenom);
  	    	 	myIntent.putExtra("nom", nom);
  	    	 	myIntent.putExtra("pseudo",pseudo);
  	    	 	myIntent.putExtra("mail", mail);
  	    	 	myIntent.putExtra("mdp", mdp);
  	    	 	myIntent.putExtra("promo", promo);
  	    	 	Select_promo.this.startActivity(myIntent);
				//fin du onclick
			}
		});
    }

}

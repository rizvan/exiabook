package net.privatenav.exia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Inscription extends Activity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
       
        

        Button _btn = (Button)findViewById(R.id.button1);
        _btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			EditText _prenom = (EditText)findViewById(R.id.editText1);
			EditText _nom = (EditText)findViewById(R.id.editText2);
			EditText _mail = (EditText)findViewById(R.id.editText3);
			EditText _mdp = (EditText)findViewById(R.id.editText4);
			EditText _pseudo = (EditText)findViewById(R.id.editText5);
				
				Intent myIntent = new Intent(Inscription.this, Select_promo.class);
				myIntent.putExtra("prenom", _prenom.getText().toString());
  	    	 	myIntent.putExtra("nom", _nom.getText().toString());
  	    	 	myIntent.putExtra("mail", _mail.getText().toString());
  	    	 	myIntent.putExtra("mdp", _mdp.getText().toString());
  	    	 	myIntent.putExtra("pseudo",_pseudo.getText().toString());
  	    	 	Inscription.this.startActivity(myIntent);
				//fin du onclick
			}
		});
    }

    
        
}

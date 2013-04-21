package net.privatenav.exia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Parametres_gene extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres_gene);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_parametres_gene, menu);
        return true;
    }
}

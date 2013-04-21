package net.privatenav.exia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Parrains extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parrains);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_parrains, menu);
        return true;
    }
}

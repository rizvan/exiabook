package net.privatenav.exia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Infos_perso extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_perso);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_infos_perso, menu);
        return true;
    }
}

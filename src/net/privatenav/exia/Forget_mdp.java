package net.privatenav.exia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Forget_mdp extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_mdp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_forget_mdp, menu);
        return true;
    }
}

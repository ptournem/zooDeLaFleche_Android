package fr.ig2i.unesaisonauzoo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class LoadActivity extends Activity implements LoadDataAsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // on set le layout
        setContentView(R.layout.activity_load);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Debug", "Start");
        // on démarre l'activité en arrière plan

        LoadDataAsyncTask t = new LoadDataAsyncTask(this);
        t.setDelegate(this);
        t.execute();

    }

    @Override
    public void onProcessFinish(Boolean success) {
        Log.i("Debug","processFinish");
        if(success) {
            Intent goToMainActivity = new Intent(this, MainActivity.class);
            startActivity(goToMainActivity);
        }
    }
}

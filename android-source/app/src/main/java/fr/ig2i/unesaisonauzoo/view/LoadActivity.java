package fr.ig2i.unesaisonauzoo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import fr.ig2i.unesaisonauzoo.utils.LoadDataAsyncResponse;
import fr.ig2i.unesaisonauzoo.utils.LoadDataAsyncTask;
import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;


public class LoadActivity extends Activity implements LoadDataAsyncResponse {
    private static String ERR_LOAD = "Impossible de charger les données";

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
        Log.i("Debug", "processFinish");
        if (!success) {
            UneSaisonAuZooApplication application = (UneSaisonAuZooApplication) getApplication();
            application.alerter(ERR_LOAD);
        }
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);

    }
}
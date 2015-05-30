package fr.ig2i.unesaisonauzoo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;
import fr.ig2i.unesaisonauzoo.utils.LoadDataAsyncResponse;
import fr.ig2i.unesaisonauzoo.utils.LoadDataAsyncTask;


// implémente LoadDataAsyncResponse pour pouvoir communiquer avec la tache asynchrone
public class LoadActivity extends Activity implements LoadDataAsyncResponse {
    private static String ERR_LOAD = "Impossible de charger les données";

    LoadDataAsyncTask asynTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // on set le layout
        setContentView(R.layout.activity_load);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on démarre le traitement en arrière plan
        asynTask = new LoadDataAsyncTask(this);
        asynTask.setDelegate(this);
        asynTask.execute();

    }

    // en fin de tâche asynchrone
    @Override
    public void onProcessFinish(Boolean success) {
        // si il y a eu une erreur
        if (!success) {
            // on récupère l'application
            UneSaisonAuZooApplication application = (UneSaisonAuZooApplication) getApplication();
            // on  alerte l'utilisateur que l'on a pas pu charger les données
            application.alerter(ERR_LOAD);
        }

        // on se rend vers l'activité principale
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);
        // on finie l'activité pour ne pas pouvoir revenir dessus
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        asynTask.cancel(true);
    }
}

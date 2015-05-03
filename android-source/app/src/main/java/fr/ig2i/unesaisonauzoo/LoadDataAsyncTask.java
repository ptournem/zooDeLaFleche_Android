package fr.ig2i.unesaisonauzoo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Paul on 29/04/2015.
 * // Params, Progress, Result
 */
public class LoadDataAsyncTask extends AsyncTask<Application, Void, Boolean> {
    private LoadDataAsyncResponse delegate = null;
    private Activity activity;
    private UneSaisonAuZooApplication application;
    private Boolean loadProgram = true;
    private Boolean loadSpectaclePlanning = true;

    public LoadDataAsyncTask(Activity a) {
        activity = a;
        application = (UneSaisonAuZooApplication) a.getApplication();
    }

    public LoadDataAsyncTask(Activity a, Boolean loadProgram, Boolean loadSpectaclePlanning) {

        this.loadProgram = loadProgram;
        this.loadSpectaclePlanning = loadSpectaclePlanning;
        new LoadDataAsyncTask(a);
    }

    public void setDelegate(LoadDataAsyncResponse delegate) {
        this.delegate = delegate;
        Log.i("Debug", "SetDelegate");
    }


    @Override
    protected Boolean doInBackground(Application... qs) {
        Boolean success = true;
        // on récupère les données que l'on injecte dans l'application
        Log.i("Debug", "doInBackground");
        //TODO : remplacer le sleep par la récupération du programme tv + du planning des spectacles
        if (!application.verifReseau()) {
            return false;
        }

        // récupération des data programme tv
        if (this.loadProgram) {
            LoadDataTVProgram loadTvProgram = new LoadDataTVProgram(activity);
            try {
                // on set la list des programmes
                application.setProgrammeList(loadTvProgram.getData());
            } catch (IOException e) {
                success = false;
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                success = false;
                e.printStackTrace();
            }
        }

        if (!application.verifReseau()) {
            return false;
        }

        // récupération du planning
        if (this.loadSpectaclePlanning) {

        }

        return success;

    }

    protected void onPostExecute(Boolean result) {
        Log.i("Debug", "onPostExecute");
        // on appelle le délégue si il n'est pas nukk
        if (delegate != null) {
            delegate.onProcessFinish(result);
        }

    }
}

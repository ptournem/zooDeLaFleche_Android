package fr.ig2i.unesaisonauzoo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Paul on 29/04/2015.
 * // Params, Progress, Result
 */
public class LoadDataAsyncTask extends AsyncTask<Application, Void,Boolean>{
    private LoadDataAsyncResponse delegate = null;
    private Activity activity;

    public LoadDataAsyncTask(Activity a){
        activity = a;
    }

    public void setDelegate(LoadDataAsyncResponse delegate) {
        this.delegate = delegate;
        Log.i("Debug","SetDelegate");
    }


    @Override
    protected Boolean  doInBackground(Application... qs) {
        // on récupère les données que l'on injecte dans l'application
        Log.i("Debug","doInBackground");
        //TODO : remplacer le sleep par la récupération du programme tv + du planning des spectacles
        try {
            LoadDataTVProgram loadTvProgram = new LoadDataTVProgram(activity);
            Log.i("Debug", loadTvProgram.getData());

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;

    }

    protected void onPostExecute(Boolean result) {
        Log.i("Debug","onPostExecute");
        // on appelle le délégue si il n'est pas nukk
        if(delegate!=null){
            delegate.onProcessFinish(result);
        }

    }
}

package fr.ig2i.unesaisonauzoo.utils;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;

/**
 * Created by Paul on 29/04/2015.
 * // Params, Progress, Result
 */
public class LoadDataAsyncTask extends AsyncTask<Application, Void, Boolean> {
    private LoadDataAsyncResponse delegate = null;
    private Activity activity;
    private UneSaisonAuZooApplication application;
    private Boolean loadProgram = true;

    public LoadDataAsyncTask(Activity a) {
        activity = a;
        application = (UneSaisonAuZooApplication) a.getApplication();
    }

    public LoadDataAsyncTask(Activity a, Boolean loadProgram) {

        this.loadProgram = loadProgram;
        new LoadDataAsyncTask(a);
    }

    public void setDelegate(LoadDataAsyncResponse delegate) {
        this.delegate = delegate;
    }


    @Override
    protected Boolean doInBackground(Application... qs) {
        Boolean success = true;
        // on récupère les données que l'on injecte dans l'application
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

        return success;

    }

    protected void onPostExecute(Boolean result) {
        // on appelle le délégue si il n'est pas nukk
        if (delegate != null) {
            delegate.onProcessFinish(result);
        }

    }
}

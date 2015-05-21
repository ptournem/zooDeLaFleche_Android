package fr.ig2i.unesaisonauzoo.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.adapter.EpisodeAdapter;
import fr.ig2i.unesaisonauzoo.model.Episode;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;

/**
 * Created by Mélanie on 20/05/2015.
 */
public class LoadAsyncTaskEpisode extends AsyncTask<Void, Void, List<Episode>> {
    private Activity activity;
    private UneSaisonAuZooApplication application;


    public LoadAsyncTaskEpisode(Activity a) {
        activity = a;
        application = (UneSaisonAuZooApplication) a.getApplication();
    }


    @Override
    protected List<Episode> doInBackground(Void... params) {
        Boolean success = true;

        Log.i("Debug", "doInBackground");
        if (!application.verifReseau()) {
            return null;
        }
        // récupération episode
        LoadEpisode loadEpisode = new LoadEpisode(activity);
        return loadEpisode.getData();
    }

    protected void onPostExecute(List<Episode> result) {
        Log.i("Debug", "onPostExecute");
        ListView episodes = (ListView) activity.findViewById(R.id.lEpisodeTV);
        EpisodeAdapter adapter = new EpisodeAdapter(result,activity.getApplicationContext());
        episodes.setAdapter(adapter);
    }


}

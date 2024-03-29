package fr.ig2i.unesaisonauzoo.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.List;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.adapter.EpisodeAdapter;
import fr.ig2i.unesaisonauzoo.model.Episode;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;
import fr.ig2i.unesaisonauzoo.view.fragment.EpisodeFragment;

/**
 * Created by Melanie on 20/05/2015.
 */
public class LoadAsyncTaskEpisode extends AsyncTask<Void, Void, List<Episode>> {

    private static String UNESAISONAUZOO_CHANNELID = "UC1v-9t_jmYxzTB6Pa4xZXpw";
    private static String ZOODELAFLECHE_CHANNELID = "UCe3gD03ffv7HlzFkuqpJPCw";
    private Activity activity;
    private int type;
    private UneSaisonAuZooApplication application;


    public LoadAsyncTaskEpisode(Activity a, int type) {
        activity = a;
        application = (UneSaisonAuZooApplication) a.getApplication();
        this.type = type;
    }


    @Override
    protected List<Episode> doInBackground(Void... params) {
        Boolean success = true;
        //on verifie la connexion internet
        if (!application.verifReseau()) {
            return null;
        }

        // recuperation episode de la chaine unesaisonauzoo
        // recuperation des videos du zoo de la fleche
        // recherche du type afin d'utiliser le meme fragment
        LoadEpisode loadEpisode = new LoadEpisode(activity, type == EpisodeFragment.TYPE_EPISODE ? UNESAISONAUZOO_CHANNELID : ZOODELAFLECHE_CHANNELID);
        return loadEpisode.getData();
    }

    protected void onPostExecute(List<Episode> result) {
        // on recupere la listView
        ListView episodes = (ListView) activity.findViewById(R.id.lEpisodeTV);
        // on cree l'adapter
        EpisodeAdapter adapter = new EpisodeAdapter(result, activity.getApplicationContext());
        // on assigne l'adapter
        episodes.setAdapter(adapter);
    }


}

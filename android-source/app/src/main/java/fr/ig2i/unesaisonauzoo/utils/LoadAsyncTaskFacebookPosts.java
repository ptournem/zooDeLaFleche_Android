package fr.ig2i.unesaisonauzoo.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.List;

import fr.ig2i.unesaisonauzoo.adapter.FacebookPostsAdapter;
import fr.ig2i.unesaisonauzoo.model.FacebookPost;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;

/**
 * Created by Paul on 30/05/2015.
 */
public class LoadAsyncTaskFacebookPosts extends AsyncTask<Void,Void,List<FacebookPost>> {

    private Activity activity;
    private UneSaisonAuZooApplication application;
    private ListView LVFacebookPosts;

    public LoadAsyncTaskFacebookPosts(Activity activity, ListView LVFacebookPosts) {
        this.activity = activity;
        application = (UneSaisonAuZooApplication) activity.getApplication();
        // recuperation de la listView
        this.LVFacebookPosts = LVFacebookPosts;
    }

    @Override
    protected List<FacebookPost> doInBackground(Void...  Params) {
        Boolean success = true;
        //on verifie la connexion internet
        if (!application.verifReseau()) {
            return null;
        }

        // recuperation des posts de la page facebook du zoo de la fleche
        LoadFacebookPosts loadFacebookPosts = new LoadFacebookPosts(activity);
        return loadFacebookPosts.getData();
    }

    protected void onPostExecute(List<FacebookPost> result) {

        // on cree l'adapter
        FacebookPostsAdapter adapter = new FacebookPostsAdapter(activity.getBaseContext(),result);
        // on assigne l'adapter
        LVFacebookPosts.setAdapter(adapter);
    }
}

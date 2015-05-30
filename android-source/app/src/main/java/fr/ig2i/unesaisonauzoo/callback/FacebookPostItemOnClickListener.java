package fr.ig2i.unesaisonauzoo.callback;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import fr.ig2i.unesaisonauzoo.adapter.FacebookPostsAdapter;
import fr.ig2i.unesaisonauzoo.model.FacebookPost;
import fr.ig2i.unesaisonauzoo.view.fragment.AccueilFragment;

/**
 * Created by Paul on 30/05/2015.
 */
public class FacebookPostItemOnClickListener implements AdapterView.OnItemClickListener  {

    private ListView listView;
    private AccueilFragment fragment;

    public  FacebookPostItemOnClickListener(ListView view,AccueilFragment fragment ){
        listView = view;
        this.fragment = fragment;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // recuperation de l'episode
        FacebookPostsAdapter adapter =  (FacebookPostsAdapter) listView.getAdapter();
        if(adapter == null){
            return;
        }

        // on recupere l'item episode
        FacebookPost e = adapter.getItem(position);

        // on fait l'action en passant l'id epure du post facebook au listener du fragment
        fragment.getmListener().onPostClicked(e.getUnformatedPostId());



    }

}

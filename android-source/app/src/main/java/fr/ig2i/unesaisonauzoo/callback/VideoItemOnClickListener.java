package fr.ig2i.unesaisonauzoo.callback;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import fr.ig2i.unesaisonauzoo.adapter.EpisodeAdapter;
import fr.ig2i.unesaisonauzoo.model.Episode;
import fr.ig2i.unesaisonauzoo.view.fragment.EpisodeFragment;

/**
 * Created by Melanie on 21/05/2015.
 */
public class VideoItemOnClickListener implements AdapterView.OnItemClickListener {

    private ListView listView;
    private EpisodeFragment fragment;

    public  VideoItemOnClickListener(ListView view,EpisodeFragment fragment ){
        listView = view;
        this.fragment = fragment;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // recuperation de l'episode
        EpisodeAdapter adapter =  (EpisodeAdapter) listView.getAdapter();
        if(adapter == null){
            return;
        }

        // on recupere l'item episode
        Episode e = (Episode) adapter.getItem(position);

        // on fait l'action en passant l'id de l'episode au listener du fragment
        fragment.getmListener().OnVideoItemOnClickListener(e.id);



    }
}

package fr.ig2i.unesaisonauzoo.callback;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

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

        Episode e = (Episode) adapter.getItem(position);



        // on fait l'action
        fragment.getmListener().OnVideoItemOnClickListener(e.id);



    }
}

package fr.ig2i.unesaisonauzoo.view.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.callback.VideoItemOnClickListener;
import fr.ig2i.unesaisonauzoo.utils.LoadAsyncTaskEpisode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpisodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpisodeFragment extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EpisodeFragment.
     */
    public static int TYPE_VIDEO = 1;
    public static int TYPE_EPISODE = 2;

    ListView episodes;
    TextView title;

    private int type;
    OnVideoItemOnClickListener mListener;

    public OnVideoItemOnClickListener getmListener() {
        return mListener;
    }
    // on met en parametre le type de la demande pour reconnaitre une video ou un episode
    public static EpisodeFragment newInstance(int type) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.type = type;
        return fragment;
    }

    public EpisodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episode, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView episode = (ListView) getActivity().findViewById(R.id.lEpisodeTV);
        episode.setOnItemClickListener(new VideoItemOnClickListener(episode, this));

        LoadAsyncTaskEpisode asyncTask = new LoadAsyncTaskEpisode(getActivity(),this.type);
        asyncTask.execute();

        if(this.type== TYPE_VIDEO){
            // recuperation de la TextView
            title = (TextView) getActivity().findViewById(R.id.EpisodeTV);
            // si type = video, affichage du titre liste des videos
            title.setText(getResources().getString(R.string.ListeVideos));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mListener = (OnVideoItemOnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnVideoItemOnClickListener");
        }
    }

    public interface OnVideoItemOnClickListener{
        public void OnVideoItemOnClickListener(String videoId);
    }


}

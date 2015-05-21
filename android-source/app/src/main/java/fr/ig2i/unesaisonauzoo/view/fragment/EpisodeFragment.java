package fr.ig2i.unesaisonauzoo.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fr.ig2i.unesaisonauzoo.R;
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
    // TODO: Rename and change types and number of parameters

    ListView episodes;

    public static EpisodeFragment newInstance() {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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

        LoadAsyncTaskEpisode asyncTask = new LoadAsyncTaskEpisode(getActivity());
        asyncTask.execute();
    }


}

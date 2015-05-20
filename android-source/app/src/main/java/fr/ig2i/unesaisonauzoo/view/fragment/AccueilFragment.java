package fr.ig2i.unesaisonauzoo.view.fragment;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import fr.ig2i.unesaisonauzoo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccueilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccueilFragment extends ListFragment    {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AccueilFragment.
     */
    public static AccueilFragment newInstance() {
        AccueilFragment fragment = new AccueilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("zoodelafleche")
                .build();
        TweetTimelineListAdapter tweetTimelineListAdapter = new TweetTimelineListAdapter(getActivity(), userTimeline);
        setListAdapter(tweetTimelineListAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accueil, container, false);
    }
}

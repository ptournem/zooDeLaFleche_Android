package fr.ig2i.unesaisonauzoo.view.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.callback.FacebookPostItemOnClickListener;
import fr.ig2i.unesaisonauzoo.utils.LoadAsyncTaskFacebookPosts;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccueilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccueilFragment extends ListFragment    {

    ListView LVFacebookPosts;

    OnPostClickedListener mListener;

    public OnPostClickedListener getmListener() {
        return mListener;
    }

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
        // recuperation de la timeline du zoo de la fleche
        UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("zoodelafleche")
                .build();
        //creation de l'adapter
        TweetTimelineListAdapter tweetTimelineListAdapter = new TweetTimelineListAdapter(getActivity(), userTimeline);
        // assignation de l'adapter
        setListAdapter(tweetTimelineListAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accueil, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // on recupere la listView pour les posts Facebook
        LVFacebookPosts = (ListView) getActivity().findViewById(R.id.LV_Facebook);

        // ajoute le onClickListener sur la listView
        LVFacebookPosts.setOnItemClickListener(new FacebookPostItemOnClickListener(LVFacebookPosts,this));


        // on recupere les donnees en passant la listView pour lui mettre l'adapter
        LoadAsyncTaskFacebookPosts task = new LoadAsyncTaskFacebookPosts(getActivity(),LVFacebookPosts);
        task.execute();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // on recupere le listener pour les clicks sur post FB
        try{
            mListener = (OnPostClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnPostClickedListener");
        }
    }

    // interface de dialog
    public interface  OnPostClickedListener{
        public void onPostClicked(String link);
    }
}

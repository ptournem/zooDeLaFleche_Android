package fr.ig2i.unesaisonauzoo.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.MapFragment;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.Calendar;
import java.util.Date;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.callback.MapAsyncCallback;
import fr.ig2i.unesaisonauzoo.view.fragment.AccueilFragment;
import fr.ig2i.unesaisonauzoo.view.fragment.EpisodeFragment;
import fr.ig2i.unesaisonauzoo.view.fragment.NavigationDrawerFragment;
import fr.ig2i.unesaisonauzoo.view.fragment.ProgrammeTvFragment;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ProgrammeTvFragment.OnCalendarButtonClickedListener, EpisodeFragment.OnVideoItemOnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "4YuvZ1XTztatfTfqjX5R6oWNz";
    private static final String TWITTER_SECRET = "YvRwDYKs1NQWIX6Yj3aXexs4ycbZMtGV37OXb0VxSepOfX4AVZ";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment next = null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        MapFragment map = null;

        // récupération du prochain fragment
        switch (position) {
            case 0:// accueil
                next = AccueilFragment.newInstance();
                break;
            case 1: // accès
                map = MapFragment.newInstance();
                map.getMapAsync(new MapAsyncCallback(this));
                next = map;
                break;
            case 2:// programme tv
                next = ProgrammeTvFragment.newInstance();
                break;
            case 4: // épisode depuis youtube
                next = EpisodeFragment.newInstance();
                break;
            default:
                next = PlaceholderFragment.newInstance(position + 1);
                break;
        }

        // passage au prochain fragment
        transaction.replace(R.id.container, next);
        // on stock dans le backStack
        transaction.addToBackStack(null);
        // on commit le changement
        transaction.commit();


    }

    public void onSectionAttached(int number) {
        String[] sectionName = getResources().getStringArray(R.array.sectionName);
        if (sectionName.length > (number - 1)) {
            mTitle = sectionName[number - 1];
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnCalendarButtonClicked(Date startDt, String progName, Date endDt) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(startDt);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(endDt);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, progName);
        startActivity(intent);
    }

    /**
     * lorsque le bouton back est utilisé
     */
    public void onBackPressed() {
        // si il reste des fragments dans la pile
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            // on pop le dernier fragment
            getFragmentManager().popBackStack();
            // on mets le dernier item sélectionner dans le navigationDrawer
            mNavigationDrawerFragment.goBackToLastSelectedItem();
        }
        // sinon, on fait l'action par défaut = revenir vers la précédente activité si elle existe
        else super.onBackPressed();
    }

    @Override
    public void OnVideoItemOnClickListener(String videoId) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/embed/" + videoId)));
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}

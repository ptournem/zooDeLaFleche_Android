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
            case 3: // épisode depuis youtube
                next = EpisodeFragment.newInstance(EpisodeFragment.TYPE_EPISODE);
                break;
            case 4: //video depuis youtube
                next = EpisodeFragment.newInstance(EpisodeFragment.TYPE_VIDEO);
                break;
            default:
                next = null;
                break;
        }

        // on verifie que l'on a bien un fragment
        if (next != null) {
            // passage au prochain fragment
            transaction.replace(R.id.container, next);
            // on stock dans le backStack
            transaction.addToBackStack(null);
            // on commit le changement
            transaction.commit();
        }

    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    // lorsque l'on clic sur le calendrier d'un programme
    @Override
    public void OnCalendarButtonClicked(Date startDt, String progName, Date endDt) {
        // on récupère la date de début
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(startDt);
        // on récupère la date de fin
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(endDt);

        // on crée l'intent d'insertion dans le calendrier
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, progName);

        // on démarre l'intent
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

    // lorsque l'on clique sur une vidéo
    @Override
    public void OnVideoItemOnClickListener(String videoId) {
        // on démarre un intent de visualisation de la vidéo dont on a l'id
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/embed/" + videoId)));
    }

}

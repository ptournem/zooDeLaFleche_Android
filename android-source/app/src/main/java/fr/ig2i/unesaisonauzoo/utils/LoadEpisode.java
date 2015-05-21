package fr.ig2i.unesaisonauzoo.utils;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.ig2i.unesaisonauzoo.model.Episode;
import fr.ig2i.unesaisonauzoo.model.Programme;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;

/**
 * Created by M�lanie on 20/05/2015.
 */
public class LoadEpisode {
    private static String REQUEST_URL = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyCZDxFeGRHAXz7Bp57HmxPxESxouPJzzWc&channelId=UC1v-9t_jmYxzTB6Pa4xZXpw&part=snippet,id&order=date&maxResults=20";

    private Activity a;
    private UneSaisonAuZooApplication application;

    public LoadEpisode(Activity a) {
        this.a = a;
        this.application = (UneSaisonAuZooApplication) a.getApplication();


    }

    public List<Episode> getData() {
        List<Episode> episodes = new ArrayList<Episode>();
        String title;
        String desc;
        String thumbnail;
        String id;

        try {

            String json = application.requete(REQUEST_URL, null);
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray items = jsonObject.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    title = desc = thumbnail = id = null;

                    try {
                        JSONObject item = items.getJSONObject(i);
                        id = item.getJSONObject("id").getString("videoId");
                        JSONObject snippet = item.getJSONObject("snippet");
                        title = snippet.getString("title");
                        desc = snippet.getString("description");
                        thumbnail = snippet.getJSONObject("thumbnails").getJSONObject("default").getString("url");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // v�rifier que title, desc et thumbnail et id ne sont pas null
                    if (title == null || desc == null || thumbnail == null || id == null) {
                        continue;
                    }
                    episodes.add(new Episode(title, desc, thumbnail, id));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return episodes;
    }
}


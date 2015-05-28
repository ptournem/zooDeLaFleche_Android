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
 * Created by Melanie on 20/05/2015.
 */
public class LoadEpisode {
    private static String REQUEST_URL_TEMPLATE = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyCZDxFeGRHAXz7Bp57HmxPxESxouPJzzWc&channelId={channelId}&part=snippet,id&order=date&maxResults=20";

    private Activity a;
    private String request;
    private UneSaisonAuZooApplication application;

    public LoadEpisode(Activity a,String channelId) {
        this.a = a;
        this.application = (UneSaisonAuZooApplication) a.getApplication();
        // recuperation du channel_ID
        this.request = REQUEST_URL_TEMPLATE.replace("{channelId}",channelId);

    }

    public List<Episode> getData() {
        List<Episode> episodes = new ArrayList<Episode>();
        String title;
        String desc;
        String thumbnail;
        String id;
        Boolean isVideo;

        try {

            String json = application.requete(this.request, null);
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray items = jsonObject.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    // initialisation des variables
                    title = desc = thumbnail = id = null;
                    isVideo = false;

                    try {
                        // récupération de l'objet item
                        JSONObject item = items.getJSONObject(i);

                        // isVideo est vrai si on a bien une video youtube dans le paramètre kind et non autre chose
                        isVideo = item.getJSONObject("id").getString("kind").equals("youtube#video");

                        // si isVidéo est false, on passe à l'item suivant
                        if(!isVideo){
                            continue;
                        }
                        // récupération de l'id de la video
                        id = item.getJSONObject("id").getString("videoId");
                        // récupération du snippet
                        JSONObject snippet = item.getJSONObject("snippet");

                        // récupération du title et de la description depuis le snippet
                        title = snippet.getString("title");
                        desc = snippet.getString("description");

                        // récupération de l'url de la miniature
                        thumbnail = snippet.getJSONObject("thumbnails").getJSONObject("default").getString("url");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // verifier que title, desc et thumbnail et id ne sont pas null
                    if (title == null || desc == null || thumbnail == null || id == null) {
                        continue;
                    }
                    // ajout de l'épisode dans la liste
                    episodes.add(new Episode(title, desc, thumbnail, id));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // on renvoie la liste des épisodes
        return episodes;
    }
}


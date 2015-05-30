package fr.ig2i.unesaisonauzoo.utils;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ig2i.unesaisonauzoo.model.FacebookPost;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;

/**
 * Created by Paul on 30/05/2015.
 */
public class LoadFacebookPosts {
    private Activity a;
    private UneSaisonAuZooApplication application;
    SimpleDateFormat dateParse;

    private static String PAGE_ID = "318319208254989"; // id de la page facebook du zoo de la fleche
    private static String APP_ID = "475689602589994";//app id de l'application facebook
    private static String SECRET_ID = "59af96a96a152d95c1d8f0f6b3fbe98c"; // secret id de l'application facebook


    private static String request;

    public LoadFacebookPosts(Activity a) {
        // on stocke l'activite et l'application
        this.a = a;
        this.application = (UneSaisonAuZooApplication) a.getApplication();

        // on cree la requete
        try {
            request = "https://graph.facebook.com/" + PAGE_ID + "/posts?key=value&access_token=" + APP_ID + URLEncoder.encode("|", "UTF-8") + SECRET_ID + "&fields=id,name,description,link,message,full_picture,updated_time,from";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // parser de date
        dateParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    }

    // function pour recuperer les posts
    protected List<FacebookPost> getData() {
        // var pour parser
        String id, message, link, picture, description, name, datetime, from;
        JSONObject obFrom;
        Date date;

        // creation de l'arraylist
        ArrayList<FacebookPost> postsList = new ArrayList<FacebookPost>();

        // recuperation de la chaine JSON
        String json = application.requete(request, null);

        // si elle n'est pas vide
        if (json != "") {
            try {
                // on la parse
                JSONObject jsonObj = new JSONObject(json);
                // on recupere le tableau des datas
                JSONArray posts = jsonObj.getJSONArray("data");

                // pour chaque posts
                for (int i = 0; i < posts.length(); i++) {
                    id = message = picture = link = description = name = datetime = from = null;
                    date = null;
                    obFrom = null;
                    // on recupere l'objet POST
                    JSONObject c = posts.getJSONObject(i);

                    //recuperation de l'objet from puis du nom
                    obFrom = (c.has("from")) ? c.getJSONObject("from") : null;
                    if (obFrom != null) {
                        from = (obFrom.has("name")) ? obFrom.getString("name") : null;
                    }


                    id = (c.has("id")) ? c.getString("id") : null;
                    // recuperation du message
                    message = (c.has("message")) ? c.getString("message") : null;
                    // recuperation de l'image
                    picture = (c.has("full_picture")) ? c.getString("full_picture") : null;
                    // recuperation du lien
                    link = (c.has("link")) ? c.getString("link") : "";
                    // recuperation de la description
                    description = (c.has("description")) ? c.getString("description") : null;
                    // recuperation du nom
                    name = (c.has("name")) ? c.getString("name") : null;
                    // recuperation de la date du post
                    datetime = (c.has("updated_time")) ? c.getString("updated_time") : null;
                    try {
                        date = dateParse.parse(datetime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // si il manque des infos, on n'ajoute pas le post
                    if (id == null || link == null || date == null || from == null || (message == null && name == null && picture == null)) {
                        continue;
                    }

                    // ajout du post dans l'arrayList
                    postsList.add(new FacebookPost(id, message, link, name, description, picture, date, from));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // on renvoie les donnees
        return postsList;
    }

}

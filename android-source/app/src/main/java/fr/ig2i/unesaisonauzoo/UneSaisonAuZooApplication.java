package fr.ig2i.unesaisonauzoo;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.Map;

/**
 * Created by Paul on 02/05/2015.
 */
public class UneSaisonAuZooApplication extends Application {



    public boolean verifReseau()
    {
        // On vérifie si le réseau est disponible,
        // si oui on change le statut du bouton de connexion
        ConnectivityManager cnMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cnMngr.getActiveNetworkInfo();


        Boolean bStatut = false;
        if (netInfo != null)
        {
            NetworkInfo.State netState = netInfo.getState();

            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0)
            {
                bStatut = true;
                // on se fiche du type de réseau utilisé
                /*int netType= netInfo.getType();
                switch (netType)
                {
                    case ConnectivityManager.TYPE_MOBILE :
                    case ConnectivityManager.TYPE_WIFI :
                }*/

            }
        }

        // a completer : doit renvoyer un booleen fonction de l'état du réseau
        return bStatut;
    }

    /**
     *  Renvoie le texte pour une requête
     * @param adresse adresse url
     * @param data map pour les données post
     * @return String
     */
    public String requete(String adresse, Map<String,Object> data ){
        String donnees = "";
        String ligne = "";
        System.setProperty("http.keepAlive", "false");
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        URLConnection connexion = null;
        try {
            if(data!=null) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    donnees += URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue().toString(), "UTF-8");
                }
            }

            // On a envoyé les données à une adresse distante
            URL url = new URL(adresse);
            connexion = url.openConnection();
            connexion.setDoOutput(true);

            // On envoie la requête ici
            writer = new OutputStreamWriter(connexion.getOutputStream());

            // On insère les données dans notre flux
            writer.write(donnees);

            // Et on s'assure que le flux est vidé
            writer.flush();

            // On lit la réponse ici
            reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));

            String templigne;
            // Tant que « ligne » n'est pas null, c'est que le flux n'a pas terminé d'envoyer des informations
            while((templigne = reader.readLine()) != null) {
                ligne+=templigne;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{writer.close();}catch(Exception e){}
            try{reader.close();}catch(Exception e){}
        }

        return ligne;
    }
}

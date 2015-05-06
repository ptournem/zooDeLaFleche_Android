package fr.ig2i.unesaisonauzoo.model;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import fr.ig2i.unesaisonauzoo.model.Programme;

/**
 * Created by Paul on 02/05/2015.
 */
public class UneSaisonAuZooApplication extends Application {

    private List<Programme> programmeList = null;

    public List<Programme> getProgrammeList() {
        return programmeList;
    }

    public void setProgrammeList(List<Programme> programmeList) {
        this.programmeList = programmeList;
    }

    public boolean verifReseau() {
        // On vérifie si le réseau est disponible,
        ConnectivityManager cnMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cnMngr.getActiveNetworkInfo();


        Boolean bStatut = false;
        if (netInfo != null) {
            NetworkInfo.State netState = netInfo.getState();

            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0) {
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

    public InputStream getDataStream(String adresse, Map<String, Object> data) {
        String donnees = "";
        System.setProperty("http.keepAlive", "false");
        OutputStreamWriter writer = null;
        InputStream reader = null;
        URLConnection connexion = null;
        try {
            if (data != null) {
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

            // on se connecte
            connexion.connect();
            // On lit la réponse ici
            reader = connexion.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }

        }
        return reader;
    }

    /**
     * Renvoie le texte pour une requête
     *
     * @param adresse adresse url
     * @param data    map pour les données post
     * @return String
     */
    public String requete(String adresse, Map<String, Object> data) {
        String templigne;
        String ligne = "";

        // on recupère l'inputStream
        InputStream reader = this.getDataStream(adresse, data);
        // le bufferedReader
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(reader));
        // Tant que « ligne » n'est pas null, c'est que le flux n'a pas terminé d'envoyer des informations
        try {
            while ((templigne = buffReader.readLine()) != null) {
                ligne += templigne;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffReader.close();
            } catch (Exception e) {
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // on retourne le string
        return ligne;
    }

    public void alerter(String s) {
        Toast t = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        t.show();
    }
}

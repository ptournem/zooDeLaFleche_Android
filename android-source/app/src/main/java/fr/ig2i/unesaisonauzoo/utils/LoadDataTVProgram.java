package fr.ig2i.unesaisonauzoo.utils;

import android.app.Activity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fr.ig2i.unesaisonauzoo.model.Programme;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;

/**
 * Created by Paul on 02/05/2015.
 */
public class LoadDataTVProgram {

    private static String PRG_URL = "http://unesaisonauzoo.paultournemaine.fr/programme.php"; // utilise un cache pour être sur qu'il y ait toujour un retour
    private Activity a;
    private UneSaisonAuZooApplication application;

    public LoadDataTVProgram(Activity a) {
        this.a = a;
        this.application = (UneSaisonAuZooApplication) a.getApplication();
    }

    public List<Programme> getData() throws IOException, XmlPullParserException {
        InputStream stream = null;
        List<Programme> programmes = null;
        // récupération du parser
        ProgrammeTvParser programmeTvParser = new ProgrammeTvParser();

        try {
            // on récupère le stream des datas
            stream = application.getDataStream(PRG_URL, null);
            // on le parse
            programmes = programmeTvParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        // on retourne la liste des programmes
        return programmes;
    }
}

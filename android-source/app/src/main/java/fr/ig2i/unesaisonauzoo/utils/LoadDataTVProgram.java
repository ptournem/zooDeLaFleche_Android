package fr.ig2i.unesaisonauzoo.utils;

import android.app.Activity;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;
import fr.ig2i.unesaisonauzoo.model.Programme;

/**
 * Created by Paul on 02/05/2015.
 */
public class LoadDataTVProgram {

    private static String PRG_URL = "http://192.168.1.68/programme.xml";
    private Activity a;
    private UneSaisonAuZooApplication application;

    public LoadDataTVProgram(Activity a) {
        this.a = a;
        this.application = (UneSaisonAuZooApplication) a.getApplication();
    }

    public List<Programme> getData() throws IOException, XmlPullParserException {
        InputStream stream = null;
        List<Programme> programmes = null;
        ProgrammeTvParser programmeTvParser = new ProgrammeTvParser();

        String xml = application.requete(PRG_URL, null);
        Log.i("XML", xml);

        try {
            stream = application.getDataStream(PRG_URL, null);
            programmes = programmeTvParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return programmes;
    }
}

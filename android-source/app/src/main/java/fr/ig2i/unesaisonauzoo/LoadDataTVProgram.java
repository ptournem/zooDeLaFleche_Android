package fr.ig2i.unesaisonauzoo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.List;

import fr.ig2i.unesaisonauzoo.metier.Programme;

/**
 * Created by Paul on 02/05/2015.
 */
public class LoadDataTVProgram {

    private static String PRG_URL = "http://www.kazer.org/tvguide.xml?u=86s5kv3ubwbdz";
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

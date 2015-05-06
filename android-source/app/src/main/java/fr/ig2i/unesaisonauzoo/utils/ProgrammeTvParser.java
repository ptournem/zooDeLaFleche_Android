package fr.ig2i.unesaisonauzoo.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import fr.ig2i.unesaisonauzoo.model.Programme;

/**
 * Created by Paul on 03/05/2015.
 */
public class ProgrammeTvParser {

    public static String PROGRAMME_TAG = "programme";
    public static String START_TAG = "tv";
    public static String TITLE_TAG = "title";
    public static String SUBTITLE_TAG = "sub-title";
    public static String DESC_TAG = "desc";
    public static String LENGTH_TAG = "length";
    public static String START_ATTR = "start";
    public static String STOP_ATTR = "stop";

    private Pattern titleProgramme = null;

    public ProgrammeTvParser() {
        titleProgramme = Pattern.compile(".*saison.*zoo");
    }

    // We don't use namespaces
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readTvProgram(parser);
        } finally {
            in.close();
        }
    }

    private List readTvProgram(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
        Programme p = null;

        parser.require(XmlPullParser.START_TAG, ns, START_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(PROGRAMME_TAG)) {
                p = readProgramme(parser); // readProgramme renvoie null, si ce n'est pas un zoo de la flèche
                if (p != null) {    // on l'ajoute si il n'est pas null
                    entries.add(p);
                }
            } else {
                skip(parser);
            }
        }

        return entries;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private Programme readProgramme(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, PROGRAMME_TAG);
        String title = null;
        String subtitle = null;
        String stStart = null;
        Date start = null;
        String stStop = null;
        Date stop = null;
        String length = null;
        String desc = null;

        // récupération de la variable start
        SimpleDateFormat xmlDateFormat = new SimpleDateFormat("yyyyMMddHHmmss Z");
        stStart = parser.getAttributeValue(ns, START_ATTR);
        if (stStart != null) {
            // TODO on mets en forme la date, le format est yyyymmddhhiiss +gmt
            try {
                start = xmlDateFormat.parse(stStart);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // récupération de la variable stop
        stStop = parser.getAttributeValue(ns, STOP_ATTR);
        if (stStop != null) {
            // TODO on mets en forme la date, le format est yyyymmddhhiiss +gmt
            try {
                stop = xmlDateFormat.parse(stStart);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TITLE_TAG)) {
                title = readTitle(parser);
            } else if (name.equals(LENGTH_TAG)) {
                length = readLength(parser);
            } else if (name.equals(SUBTITLE_TAG)) {
                subtitle = readSubtitle(parser);
            } else if (name.equals(DESC_TAG)) {
                desc = readDesc(parser);
            } else {
                skip(parser);
            }
        }

        // TODO : Filtre sur les programmes avec un match
        if (titleProgramme.matcher(title).matches()) {
            return new Programme(start, stop, title, subtitle, desc, length);
        }
        return null;
    }

    private String readSubtitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, SUBTITLE_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, SUBTITLE_TAG);
        return title;
    }

    private String readLength(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, LENGTH_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, LENGTH_TAG);
        return title;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TITLE_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TITLE_TAG);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private String readDesc(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, DESC_TAG);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, DESC_TAG);
        return title;
    }
}

package fr.ig2i.unesaisonauzoo.metier;

import java.util.Date;

/**
 * Created by Paul on 03/05/2015.
 */
public class Programme {
    public final Date start;
    public final Date stop;
    public final String title;
    public final String subtitle;
    public final String desc;
    public final String length;

    public Programme(Date start, Date stop, String title, String subtitle, String desc, String length) {
        this.start = start;
        this.stop = stop;
        this.title = title;
        this.subtitle = subtitle;
        this.desc = desc;
        this.length = length;
    }
}

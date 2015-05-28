package fr.ig2i.unesaisonauzoo.model;

import java.util.Date;

/**
 * Created by Melanie on 20/05/2015.
 */
public class Episode {

    public final String title;
    public final String desc;
    public final String thumbnails;
    public final String id;


    public Episode (String title, String desc, String thumbnails,String id) {
        this.title = title;
        this.desc = desc;
        this.thumbnails = thumbnails;
        this.id = id;

    }

}

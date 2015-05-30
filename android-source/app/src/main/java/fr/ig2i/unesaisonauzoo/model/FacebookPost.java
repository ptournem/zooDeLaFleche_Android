package fr.ig2i.unesaisonauzoo.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Paul on 30/05/2015.
 */
public class FacebookPost {
    String id;
    String message;
    String link;
    String name;
    String description;
    String picture;
    String from;
    Date updatedTime;

    SimpleDateFormat updatedTimeFormat = null;

    public FacebookPost(String id, String message, String link, String name, String description, String picture, Date updatedTime, String from) {
        this.id = id;
        this.message = message;
        this.link = link;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.updatedTime = updatedTime;
        this.from = from;

        updatedTimeFormat = new SimpleDateFormat("d MMM", Locale.FRANCE);
    }

    public FacebookPost() {
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedTimeString(){
        return updatedTimeFormat.format(getUpdatedTime());
    }

    public String getId() {
        return id;
    }

    /**
     * Renvoie l'id du post et non un string compose de  <l'id de la page _ l'id du post>
     * @return String
     */
    public String getUnformatedPostId(){
        return id.split("_")[1];
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

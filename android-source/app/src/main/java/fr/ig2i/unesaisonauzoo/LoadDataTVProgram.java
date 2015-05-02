package fr.ig2i.unesaisonauzoo;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Paul on 02/05/2015.
 */
public class LoadDataTVProgram {
    private Activity a;
    private UneSaisonAuZooApplication application;

    public LoadDataTVProgram(Activity a){
        this.a = a;
        this.application = (UneSaisonAuZooApplication) a.getApplication();
    }
    public String getData(){
        return application.requete("http://www.kazer.org/tvguide.xml?u=86s5kv3ubwbdz",null);
    }
}

package fr.ig2i.unesaisonauzoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.model.Programme;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;

/**
 * Created by Paul on 06/05/2015.
 */
public class ProgrammeTVAdapter extends BaseAdapter {

    UneSaisonAuZooApplication _application = null;
    Context _context;
    SimpleDateFormat startFormat = null;

    @Override
    public int getCount() { // on renvoie le nombre de programme dans la liste
        if(_application!=null && _application.getProgrammeList()!=null) {
            return _application.getProgrammeList().size();
        }
        return 0;
    }

    @Override
    public Programme getItem(int position) { // on renvoie l'item depuis la liste
        if(_application!=null && _application.getProgrammeList()!=null){
            return _application.getProgrammeList().get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // récupération de l'inflater
        LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // de la view
        ViewHolder holder = null;
        // Si la vue n'est pas recyclée
        if(convertView == null) {
            // On récupère le layout
            convertView  = mInflater.inflate(R.layout.adapter_programme, null);
            // on créé un viewHolder
            holder = new ViewHolder();
            // On place les widgets de notre layout dans le holder
            holder.mDesc = (TextView) convertView.findViewById(R.id.mDesc);
            holder.mLength = (TextView) convertView.findViewById(R.id.mLength);
            holder.mTitle = (TextView) convertView.findViewById(R.id.mTitle);
            holder.mStart = (TextView) convertView.findViewById(R.id.mStart);

            // puis on insère le holder en tant que tag dans le layout
            convertView.setTag(holder);
        } else {
            // Si on recycle la vue, on récupère son holder en tag
            holder = (ViewHolder)convertView.getTag();
        }

        // Dans tous les cas, on récupère le programme tv
        Programme p = getItem(position);
        // Si cet élément existe vraiment…
        if(p != null) {
            // On place dans le holder les informations sur le programme
            holder.mDesc.setText(p.desc);
            holder.mTitle.setText(p.title);
            holder.mLength.setText(p.length + " minutes");
            holder.mStart.setText(startFormat.format(p.start));
        }

        // on retourne la vue converti ou nouvelle si convertView était null
        return convertView;
    }

    // view holder pour gérer facilement la conversion de vue
    public static class ViewHolder {
        public TextView mTitle;
        public TextView mDesc ;
        public TextView mLength ;
        public TextView mStart ;
    }

    // on récupère le context pour avoir l'inflater
    // on récupère l'application pour avoir accès aux programmes
    public ProgrammeTVAdapter(Context c,UneSaisonAuZooApplication app){
        super();
        _context = c;
        _application= app;

        startFormat = new SimpleDateFormat("k:m - E d MMM y", Locale.FRANCE);
    }
}



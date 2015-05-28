package fr.ig2i.unesaisonauzoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.callback.CalendarButtonListener;
import fr.ig2i.unesaisonauzoo.model.Programme;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;
import fr.ig2i.unesaisonauzoo.view.fragment.ProgrammeTvFragment;

/**
 * Created by Paul on 06/05/2015.
 */
public class ProgrammeTVAdapter extends BaseAdapter {

    UneSaisonAuZooApplication _application = null;
    Context _context;
    ProgrammeTvFragment attachedFragment;
    SimpleDateFormat startFullFormat = null;
    SimpleDateFormat startHourFormat = null;

    @Override
    public int getCount() { // on renvoie le nombre de programme dans la liste
        if (_application != null && _application.getProgrammeList() != null) {
            return _application.getProgrammeList().size();
        }
        return 0;
    }

    @Override
    public Programme getItem(int position) { // on renvoie l'item depuis la liste
        if (_application != null && _application.getProgrammeList() != null) {
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
        Calendar now = Calendar.getInstance();
        Calendar actual = Calendar.getInstance();
        now.setTime(new Date());

        // de la view
        ViewHolder holder = null;
        // Si la vue n'est pas recyclée
        if (convertView == null) {
            // On récupère le layout
            convertView = mInflater.inflate(R.layout.adapter_programme, null);
            // on créé un viewHolder
            holder = new ViewHolder();
            // On place les widgets de notre layout dans le holder
            holder.mLength = (TextView) convertView.findViewById(R.id.mLength);
            holder.mStart = (TextView) convertView.findViewById(R.id.mStart);

            holder.calendarButton = (ImageView) convertView.findViewById(R.id.calendarButton);

            // puis on insère le holder en tant que tag dans le layout
            convertView.setTag(holder);
        } else {
            // Si on recycle la vue, on récupère son holder en tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Dans tous les cas, on récupère le programme tv
        Programme p = getItem(position);
        // Si cet élément existe vraiment…
        if (p != null) {
            // On place dans le holder les informations sur le programme
            holder.mLength.setText("(" + p.length + " minutes)");

            // on set l'heure actuelle
            actual.setTime(p.start);

            // on format l'heure de départ et on l'affiche
            if (actual.get(Calendar.YEAR) == now.get(Calendar.YEAR) && actual.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                holder.mStart.setText(_application.getResources().getString(R.string.today) + startHourFormat.format(p.start));
            } else {
                holder.mStart.setText(startFullFormat.format(p.start));
            }

            // on ajoute le listener
            holder.calendarButton.setOnClickListener(new CalendarButtonListener(attachedFragment, p.start, p.title, p.stop));
        }

        // on retourne la vue converti ou nouvelle si convertView était null
        return convertView;
    }

    // view holder pour gérer facilement la conversion de vue
    public static class ViewHolder {
        public TextView mTitle;
        public TextView mDesc;
        public TextView mLength;
        public TextView mStart;
        public ImageView calendarButton;
    }

    // on récupère le context pour avoir l'inflater
    // on récupère l'application pour avoir accès aux programmes
    public ProgrammeTVAdapter(Context c, UneSaisonAuZooApplication app, ProgrammeTvFragment attachedFragment) {
        super();
        _context = c;
        _application = app;
        this.attachedFragment = attachedFragment;
        startFullFormat = new SimpleDateFormat("E d MMM y - k:m", Locale.FRANCE);
        startHourFormat = new SimpleDateFormat(" - k:m", Locale.FRANCE);
    }

}



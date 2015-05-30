package fr.ig2i.unesaisonauzoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.model.FacebookPost;

/**
 * Created by Paul on 30/05/2015.
 */
public class FacebookPostsAdapter extends BaseAdapter {

    Context _context;
    List<FacebookPost> _posts;

    public FacebookPostsAdapter(Context _context, List<FacebookPost> _posts) {
        this._context = _context;
        this._posts = _posts;
    }

    @Override
    public int getCount() {
        return _posts.size();
    }

    @Override
    public FacebookPost getItem(int position) {
        return _posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // recuperation de l'inflater
        LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FacebookPostViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_facebook_post, null);
            viewHolder = new FacebookPostViewHolder();
            viewHolder.mMessage = (TextView) convertView.findViewById(R.id.message);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.date);
            viewHolder.mFrom = (TextView) convertView.findViewById(R.id.from);
            viewHolder.mPicture = (ImageView) convertView.findViewById(R.id.picture);

            convertView.setTag(viewHolder);
        } else {
            //recuperation de la vue
            viewHolder = (FacebookPostViewHolder) convertView.getTag();
        }
        //recuperation des episodes
        FacebookPost e = getItem(position);
        // si cette element existe
        if (e != null) {
            viewHolder.mMessage.setText(e.getMessage());
            viewHolder.mFrom.setText(e.getFrom());
            viewHolder.mDate.setText(e.getUpdatedTimeString());

            // recuperation de l'image grace a la librairie Picasso
            Picasso.with(_context).load(e.getPicture()).into(viewHolder.mPicture);

        }
        // on retourne la vue converti ou nouvelle si convertView etait null
        return convertView;
    }


    public static class FacebookPostViewHolder {
        public ImageView mPicture;
        public TextView mName;
        public TextView mMessage;
        public TextView mDescription;
        public TextView mFrom;
        public TextView mDate;
    }
}

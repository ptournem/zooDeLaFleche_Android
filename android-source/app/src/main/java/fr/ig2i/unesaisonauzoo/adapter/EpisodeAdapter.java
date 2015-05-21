package fr.ig2i.unesaisonauzoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import fr.ig2i.unesaisonauzoo.model.Episode;
import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;
import fr.ig2i.unesaisonauzoo.view.fragment.EpisodeFragment;

/**
 * Created by M�lanie on 20/05/2015.
 */
public class EpisodeAdapter extends BaseAdapter {

    Context _context;
    List<Episode> _episodes;



    public EpisodeAdapter(List<Episode> episodes, Context c){
        _context = c;
        _episodes = episodes;
    }

    @Override
    public int getCount() {
        return _episodes.size();
    }

    @Override
    public Episode getItem(int position) {
        return _episodes.get(position);
    }

    @Override
    public long getItemId(int position) {
       return  getItem(position).id.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        EpisodeViewHolder viewHolder = null;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.adapter_episode,null);
            viewHolder = new EpisodeViewHolder();
            viewHolder.mDesc = (TextView) convertView.findViewById(R.id.videoDesc);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.mThumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (EpisodeViewHolder) convertView.getTag();
        }

        Episode e = getItem(position);

        if(e!=null){
            viewHolder.mDesc.setText(e.desc);
            viewHolder.mTitle.setText(e.title);

            // TODO : faire image
            Picasso.with(_context).load(e.thumbnails).into(viewHolder.mThumbnail);

        }
        return convertView;
    }

    public static class EpisodeViewHolder{
        public ImageView mThumbnail;
        public TextView mTitle;
        public TextView mDesc;
    }
}

package app.com.br.spotifystreamer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import app.com.br.spotifystreamer.R;
import app.com.br.spotifystreamer.model.ArtistTopTrack;
import app.com.br.spotifystreamer.ws.RestClient;

/**
 * Created by brunolemgruber on 13/02/15.
 */
public class ListaAdapterTopTrack extends BaseAdapter{


    private List<ArtistTopTrack> artistTopTracks;
    private static LayoutInflater inflater = null;
    private Context context;
    private RestClient restClient;

    public ListaAdapterTopTrack(Context context, List<ArtistTopTrack> artistTopTracks) {
        this.artistTopTracks = artistTopTracks;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    static class ViewHolder{

        TextView albumName;
        TextView trackName;
        ImageView albumImage;

    }

    @Override
    public int getCount() {
        return artistTopTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return artistTopTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if(convertView==null){

            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.list_item_top_track_artist, null);
            convertView.setTag(holder);

            holder.albumName = (TextView)convertView.findViewById(R.id.albumName);
            holder.trackName = (TextView)convertView.findViewById(R.id.trackName);
            holder.albumImage = (ImageView)convertView.findViewById(R.id.albumImage);

        }else{

            holder = (ViewHolder) convertView.getTag();
        }

        holder.albumName.setText(artistTopTracks.get(position).getAlbum().getName());
        holder.trackName.setText(artistTopTracks.get(position).getTrackName());
        if(artistTopTracks.get(position).getAlbum().getImages().size() > 0)
            Picasso.with(context).load(artistTopTracks.get(position).getAlbum().getImages().get(artistTopTracks.get(position).getAlbum().getImages().size() - 2).getUrlImage()).into(holder.albumImage);

        return convertView;

    }

}

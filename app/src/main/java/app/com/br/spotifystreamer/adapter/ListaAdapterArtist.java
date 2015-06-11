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
import app.com.br.spotifystreamer.model.Artist;
import app.com.br.spotifystreamer.ws.RestClient;

/**
 * Created by brunolemgruber on 13/02/15.
 */
public class ListaAdapterArtist extends BaseAdapter{


    private List<Artist> artists;
    private static LayoutInflater inflater = null;
    private Context context;
    private RestClient restClient;

    public ListaAdapterArtist(Context context, List<Artist> artists) {
        this.artists = artists;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    static class ViewHolder{

        TextView artistName;
        ImageView artistAlbumImage;

    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public Object getItem(int position) {
        return artists.get(position);
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

            convertView = inflater.inflate(R.layout.list_item_artist, null);
            convertView.setTag(holder);

            holder.artistName = (TextView)convertView.findViewById(R.id.artist);
            holder.artistAlbumImage = (ImageView)convertView.findViewById(R.id.albumImage);

        }else{

            holder = (ViewHolder) convertView.getTag();
        }

        holder.artistName.setText(artists.get(position).getArtistName());
        if(artists.get(position).getArtistAlbumImage().size() > 0)
            Picasso.with(context).load(artists.get(position).getArtistAlbumImage().get(artists.get(position).getArtistAlbumImage().size() - 2).getUrlImage()).into(holder.artistAlbumImage);

        return convertView;

    }

}

package app.com.br.spotifystreamer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.com.br.spotifystreamer.ActivityMediaPlayer;
import app.com.br.spotifystreamer.ActivityTopTrack;
import app.com.br.spotifystreamer.R;
import app.com.br.spotifystreamer.adapter.ListAdapterTopTrack;
import app.com.br.spotifystreamer.model.Artist;
import app.com.br.spotifystreamer.model.ArtistTopTrack;
import app.com.br.spotifystreamer.ws.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by brunolemgruber on 13/02/15.
 */
public class FragmentTopTrack extends Fragment {

    private View view;
    private ListView topTrackList;
    private RestClient restClient;
    private ListAdapterTopTrack listaAdapter;
    private List<ArtistTopTrack> artistTopTracks;
    private EditText search;
    private List<Object> trackList;
    private Gson gson;
    private boolean tabletSize;
    private DialogFragment dialogFragment;
    private String artistName;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        tabletSize = getResources().getBoolean(R.bool.isTablet);

        artistName = getArguments().getString("artistName");

        setRetainInstance(true);

        search = (EditText) view.findViewById(R.id.search);
        search.setVisibility(View.GONE);

        topTrackList = (ListView) view.findViewById(R.id.listView);
        topTrackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ArtistTopTrack artistTopTrack  = (ArtistTopTrack) parent.getAdapter().getItem(position);

                if(tabletSize) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("artistsTopTracks", (Serializable) artistTopTracks);
                    bundle.putSerializable("currentSongIndex", position);
                    bundle.putString("artistName", artistName);

                    dialogFragment = FragmentMediaPlayer.newInstance();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.setTargetFragment(FragmentTopTrack.this, 1);
                    dialogFragment.show(FragmentTopTrack.this.getActivity().getSupportFragmentManager(), "dialog_track");

                }else{

                    intent = new Intent(FragmentTopTrack.this.getActivity(), ActivityMediaPlayer.class);
                    intent.putExtra("artistsTopTracks",(Serializable) artistTopTracks);
                    intent.putExtra("currentSongIndex",position);
                    intent.putExtra("artistName", artistName);
                    startActivity(intent);
                }
            }
        });

        if(!tabletSize) {

            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("TOP 10 Tracks");
            ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle(artistName);

        }else{

            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Spotify Streamer");
            ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle(artistName);
        }

        restClient = new RestClient();
        restClient.getServices().getTopTrackArtist(getArguments().getString("artistId").toString(), new Callback<Map<String, Object>>() {
            @Override
            public void success(Map<String, Object> topTrackMapResponse, Response response) {

                if (response.getStatus() == 200) {

                    Log.i("Spotify Streamer", String.valueOf(response.getStatus()));

                    trackList = (List<Object>) topTrackMapResponse.get("tracks");

                    gson = new Gson();
                    String itemsJson = gson.toJson(trackList, ArrayList.class);

                    artistTopTracks = gson.fromJson(itemsJson, new TypeToken<List<ArtistTopTrack>>() {
                    }.getType());

                    listaAdapter = new ListAdapterTopTrack(FragmentTopTrack.this.getActivity(), artistTopTracks,tabletSize);
                    topTrackList.setAdapter(listaAdapter);

                }

            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("Spotify Streamer", error.getMessage());
            }
        });

        if(artistTopTracks != null){
            listaAdapter = new ListAdapterTopTrack(FragmentTopTrack.this.getActivity(), artistTopTracks,tabletSize);
            topTrackList.setAdapter(listaAdapter);
        }else{
            artistTopTracks = new ArrayList<ArtistTopTrack>();
        }

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

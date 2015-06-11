package app.com.br.spotifystreamer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.com.br.spotifystreamer.R;
import app.com.br.spotifystreamer.adapter.ListaAdapterArtist;
import app.com.br.spotifystreamer.adapter.ListaAdapterTopTrack;
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
    private ListaAdapterTopTrack listaAdapter;
    private List<ArtistTopTrack> artistTopTracks;
    private EditText search;
    private List<Object> trackList;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        setRetainInstance(true);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("TOP 10 Tracks");
        ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle(getArguments().getString("artist_name").toString());

        search = (EditText) view.findViewById(R.id.search);
        search.setVisibility(View.GONE);

        topTrackList = (ListView) view.findViewById(R.id.listView);

        if(artistTopTracks != null){
            listaAdapter = new ListaAdapterTopTrack(FragmentTopTrack.this.getActivity(), artistTopTracks);
            topTrackList.setAdapter(listaAdapter);
        }else{
            artistTopTracks = new ArrayList<ArtistTopTrack>();
        }


        restClient = new RestClient();
        restClient.getServices().getTopTrackArtist(getArguments().getString("artist_id").toString(), new Callback<Map<String, Object>>() {
            @Override
            public void success(Map<String, Object> topTrackMapResponse, Response response) {

                if (response.getStatus() == 200) {

                    Log.i("Spotify Streamer", String.valueOf(response.getStatus()));

                    trackList = (List<Object>) topTrackMapResponse.get("tracks");

                    gson = new Gson();
                    String itemsJson = gson.toJson(trackList, ArrayList.class);

                    artistTopTracks = gson.fromJson(itemsJson, new TypeToken<List<ArtistTopTrack>>() {
                    }.getType());

                    listaAdapter = new ListaAdapterTopTrack(FragmentTopTrack.this.getActivity(), artistTopTracks);
                    topTrackList.setAdapter(listaAdapter);

                }

            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("Spotify Streamer", error.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

package app.com.br.spotifystreamer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import app.com.br.spotifystreamer.ActivityTopTrack;
import app.com.br.spotifystreamer.MainActivity;
import app.com.br.spotifystreamer.R;
import app.com.br.spotifystreamer.adapter.ListaAdapterArtist;
import app.com.br.spotifystreamer.model.Artist;
import app.com.br.spotifystreamer.ws.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by brunolemgruber on 13/02/15.
 */
public class FragmentArtist extends Fragment {

    private View view;
    private ListView artistList;
    private RestClient restClient;
    private ListaAdapterArtist listaAdapter;
    private List<Artist> artists;
    private Gson gson;
    private LinkedTreeMap<String,Object> artistMap;
    private List<Object> itemsList;
    private EditText search;
    private TextView noResult;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        setRetainInstance(true);

        noResult = (TextView) view.findViewById(R.id.not_find);

        search = (EditText) view.findViewById(R.id.search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchArtist(v.getText().toString());
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

        artistList = (ListView) view.findViewById(R.id.listView);
        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                intent = new Intent(FragmentArtist.this.getActivity(), ActivityTopTrack.class);
                intent.putExtra("artist_id",((Artist)parent.getAdapter().getItem(position)).getArtistId());
                intent.putExtra("artist_name",((Artist)parent.getAdapter().getItem(position)).getArtistName());
                startActivity(intent);
            }

        });

        if(artists != null){
            listaAdapter = new ListaAdapterArtist(FragmentArtist.this.getActivity(), artists);
            artistList.setAdapter(listaAdapter);
        }else{
            artists = new ArrayList<Artist>();
        }

        return view;
    }

    private void searchArtist(String text){

        restClient = new RestClient();
        restClient.getServices().getArtist(text, "artist", new Callback<Map<String, Object>>() {
            @Override
            public void success(Map<String, Object> artistsMapResponse, Response response) {

                if (response.getStatus() == 200) {

                    Log.i("Spotify Streamer", String.valueOf(response.getStatus()));

                    artistMap = (LinkedTreeMap<String, Object>) artistsMapResponse.get("artists");
                    itemsList = (List<Object>) artistMap.get("items");

                    if (itemsList.size() != 0) {

                        noResult.setVisibility(View.GONE);
                        artistList.setVisibility(View.VISIBLE);

                        gson = new Gson();
                        String itemsJson = gson.toJson(itemsList, ArrayList.class);

                        artists = gson.fromJson(itemsJson, new TypeToken<List<Artist>>() {
                        }.getType());

                        listaAdapter = new ListaAdapterArtist(FragmentArtist.this.getActivity(), artists);
                        artistList.setAdapter(listaAdapter);

                    } else {

                        noResult.setVisibility(View.VISIBLE);
                        artistList.setVisibility(View.GONE);

                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("Spotify Streamer", error.getMessage());
            }
        });

    }

    private void hideKeyboard() {
        search.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}

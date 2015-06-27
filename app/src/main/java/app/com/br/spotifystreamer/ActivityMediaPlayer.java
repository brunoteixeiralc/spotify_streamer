package app.com.br.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import java.io.Serializable;

import app.com.br.spotifystreamer.fragments.FragmentMediaPlayer;
import app.com.br.spotifystreamer.fragments.FragmentTopTrack;


public class ActivityMediaPlayer extends ActionBarActivity {

    private Fragment fragment;
    private FragmentTransaction ft;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        fragment = new FragmentMediaPlayer();

        if(savedInstanceState == null) {

            if(!tabletSize) {
                intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("artistsTopTracks", intent.getExtras().getSerializable("artistsTopTracks"));
                bundle.putSerializable("currentSongIndex", intent.getExtras().getSerializable("currentSongIndex"));
                bundle.putString("artistName", intent.getExtras().getString("artistName"));
                fragment.setArguments(bundle);

                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

        }

    }

}



package app.com.br.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import app.com.br.spotifystreamer.fragments.FragmentArtist;
import app.com.br.spotifystreamer.fragments.FragmentTopTrack;


public class ActivityTopTrack extends ActionBarActivity {

    private Fragment fragment;
    private FragmentTransaction ft;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new FragmentTopTrack();

        if(savedInstanceState == null) {

            intent = getIntent();
            bundle = new Bundle();
            bundle.putString("artistId", intent.getExtras().getString("artistId"));
            bundle.putString("artistName", intent.getExtras().getString("artistName"));
            fragment.setArguments(bundle);

            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            }

        }

    }



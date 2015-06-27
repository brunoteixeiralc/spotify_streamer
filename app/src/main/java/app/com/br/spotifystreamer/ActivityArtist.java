package app.com.br.spotifystreamer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import app.com.br.spotifystreamer.fragments.FragmentArtist;
import app.com.br.spotifystreamer.fragments.FragmentTopTrack;


public class ActivityArtist extends ActionBarActivity {

    private Fragment fragment;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        if(savedInstanceState == null) {

            if(!tabletSize) {

                fragment = new FragmentArtist();

                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        }
    }

}

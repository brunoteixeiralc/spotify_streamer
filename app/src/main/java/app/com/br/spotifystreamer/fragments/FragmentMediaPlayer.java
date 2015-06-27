package app.com.br.spotifystreamer.fragments;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import app.com.br.spotifystreamer.R;
import app.com.br.spotifystreamer.model.ArtistTopTrack;
import app.com.br.spotifystreamer.util.Converters;

/**
 * Created by brunolemgruber on 19/06/15.
 */
public class FragmentMediaPlayer extends android.support.v4.app.DialogFragment implements MediaPlayer.OnCompletionListener,SeekBar.OnSeekBarChangeListener{

    private TextView songArtist;
    private TextView songAlbum;
    private ImageView songImage;
    private TextView songName;
    private TextView songDurationStart;
    private TextView songDurationEnd;
    private SeekBar seekBar;
    private ImageButton ff;
    private ImageButton rw;
    private ImageButton play;
    private MediaPlayer mp;
    private Boolean tabletSize;
    private Handler mHandler = new Handler();
    private Converters converter;
    private int currentSongIndex = 0;
    private List<ArtistTopTrack> artistsTopTracks;


    public static FragmentMediaPlayer newInstance() {
        FragmentMediaPlayer f = new FragmentMediaPlayer();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_media_player, container, false);

        tabletSize = getResources().getBoolean(R.bool.isTablet);
        mp = new MediaPlayer();
        converter = new Converters();

        setRetainInstance(true);

        currentSongIndex = getArguments().getInt("currentSongIndex");
        artistsTopTracks = (List<ArtistTopTrack>) getArguments().getSerializable("artistsTopTracks");

        songArtist = (TextView) v.findViewById(R.id.songArtist);
        songAlbum = (TextView) v.findViewById(R.id.songAlbum);
        songDurationEnd = (TextView) v.findViewById(R.id.songDurationEnd);
        songDurationStart = (TextView) v.findViewById(R.id.songDurationStart);
        songImage = (ImageView) v.findViewById(R.id.songImage);
        songName = (TextView) v.findViewById(R.id.songName);
        ff = (ImageButton) v.findViewById(R.id.media_ff);
        rw = (ImageButton) v.findViewById(R.id.media_rew);
        play = (ImageButton) v.findViewById(R.id.media_play);
        seekBar = (SeekBar) v.findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(this);
        mp.setOnCompletionListener(this);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                    play.setImageResource(android.R.drawable.ic_media_play);
                }else{
                    mp.start();
                    play.setImageResource(android.R.drawable.ic_media_pause);
                }

            }
        });

        ff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentSongIndex < (artistsTopTracks.size() - 1)){
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                }else{
                    playSong(0);
                    currentSongIndex = 0;
                }
            }
        });

        rw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentSongIndex > 0){
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                }else{
                    playSong(artistsTopTracks.size() - 1);
                    currentSongIndex = artistsTopTracks.size() - 1;
                }

            }
        });

        playSong(currentSongIndex);

        return v;
    }

    public void playSong(int currentSongIndex) {

        try {

            play.setImageResource(android.R.drawable.ic_media_pause);

            songAlbum.setText(artistsTopTracks.get(currentSongIndex).getAlbum().getName());
            songName.setText(artistsTopTracks.get(currentSongIndex).getTrackName());
            songArtist.setText(getArguments().getString("artistName").toString());

            if(tabletSize)
                Picasso.with(FragmentMediaPlayer.this.getActivity()).load(artistsTopTracks.get(currentSongIndex).getAlbum().getImages().get(artistsTopTracks.get(currentSongIndex).getAlbum().getImages().size()-2).getUrlImage()).resize(250, 250).into(songImage);
            else
                Picasso.with(FragmentMediaPlayer.this.getActivity()).load(artistsTopTracks.get(currentSongIndex).getAlbum().getImages().get(artistsTopTracks.get(currentSongIndex).getAlbum().getImages().size()-2).getUrlImage()).resize(740, 740).into(songImage);

            mp.reset();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(artistsTopTracks.get(currentSongIndex).getPreviewUrl());
            mp.prepare();
            mp.start();
            seekBar.setProgress(0);
            seekBar.setMax(100);

            updateProgressBar();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            songDurationEnd.setText(""+converter.milliSecondsToTimer(totalDuration));
            songDurationStart.setText(""+converter.milliSecondsToTimer(currentDuration));

            int progress = (int)(converter.getProgressPercentage(currentDuration, totalDuration));
            seekBar.setProgress(progress);

            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onCompletion(MediaPlayer mp) {

        if(currentSongIndex < (artistsTopTracks.size() - 1)){
            playSong(currentSongIndex + 1);
            currentSongIndex = currentSongIndex + 1;
        }else{
            playSong(0);
            currentSongIndex = 0;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = converter.progressToTimer(seekBar.getProgress(), totalDuration);
        mp.seekTo(currentPosition);

        updateProgressBar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

package app.com.br.spotifystreamer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistTopTrack {

    @SerializedName("name")
    private String trackName;

    @SerializedName("album")
    private Album album;

    @SerializedName("preview_url")
    private String previewUrl;


    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}

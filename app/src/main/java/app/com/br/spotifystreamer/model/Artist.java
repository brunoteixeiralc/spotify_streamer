package app.com.br.spotifystreamer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by brunolemgruber on 05/06/15.
 */
public class Artist implements Serializable {

    @SerializedName("name")
    private String artistName;

    @SerializedName("id")
    private String artistId;

    @SerializedName("images")
    private List<Images> artistAlbumImage;


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }


    public List<Images> getArtistAlbumImage() {
        return artistAlbumImage;
    }

    public void setArtistAlbumImage(List<Images> artistAlbumImage) {
        this.artistAlbumImage = artistAlbumImage;
    }
}

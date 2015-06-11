package app.com.br.spotifystreamer.ws;

import java.util.List;
import java.util.Map;

import app.com.br.spotifystreamer.model.Artist;
import app.com.br.spotifystreamer.model.ArtistTopTrack;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by brunolemgruber on 05/02/15.
 */
public interface Services {

    @GET("/v1/search")
    public void getArtist(@Query("q") String query, @Query("type") String type, Callback<Map<String, Object>> jsonResponse);

    @GET("/v1/artists/{id}/top-tracks?country=BR")
    public void getTopTrackArtist(@Path("id") String artistId, Callback<Map<String, Object>> jsonResponse);

}

package app.com.br.spotifystreamer.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {

    private static final String BASE_URL = "http://api.spotify.com";

    private Services services;

    public RestClient()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        services = restAdapter.create(Services.class);
    }

    public Services getServices()
    {
        return services;
    }
}

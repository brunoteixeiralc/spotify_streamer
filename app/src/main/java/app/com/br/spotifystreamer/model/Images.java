package app.com.br.spotifystreamer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brunolemgruber on 05/06/15.
 */
public class Images implements Serializable {

    @SerializedName("height")
    private String height;

    @SerializedName("url")
    private String urlImage;

    @SerializedName("width")
    private String width;


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}

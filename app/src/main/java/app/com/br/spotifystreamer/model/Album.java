package app.com.br.spotifystreamer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brunolemgruber on 05/06/15.
 */
public class Album implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("images")
    private List<Images> images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }
}

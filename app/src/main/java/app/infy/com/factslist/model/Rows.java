package app.infy.com.factslist.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;

public class Rows implements Serializable{
    @SerializedName("title")
    @NonNull
    String title;

    @SerializedName("description")
    @NonNull
    String description;

    @SerializedName("imageHref")
    @NonNull
    String imageHref;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }
}

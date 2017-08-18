package com.revl.babel.challenge.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Image {

    public abstract String thumbnailUrl();

    @SerializedName("contentUrl")
    public abstract String imageUrl();
    public abstract String accentColor();

    @SerializedName("thumbnail")
    public abstract ThumbnailSize thumbnailSize();
    public abstract int width();
    public abstract int height();



    public static TypeAdapter<Image> typeAdapter(Gson gson) {
        return new AutoValue_Image.GsonTypeAdapter(gson);
    }
}

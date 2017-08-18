package com.revl.babel.challenge.model;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Images {

    @SerializedName("value")
    public abstract List<Image> images();

    public static TypeAdapter<Images> typeAdapter(Gson gson) {
        return new AutoValue_Images.GsonTypeAdapter(gson);
    }
}

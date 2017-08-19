package com.revl.babel.challenge.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ThumbnailSize implements Parcelable{
    public abstract int width();
    public abstract int height();

    public static TypeAdapter<ThumbnailSize> typeAdapter(Gson gson) {
        return new AutoValue_ThumbnailSize.GsonTypeAdapter(gson);
    }
}

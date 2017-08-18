package com.revl.babel.challenge.services;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class RevlGsonTypeAdapterFactory implements TypeAdapterFactory {

    public static TypeAdapterFactory create() {

        final TypeAdapterFactory factory = new AutoValueGson_RevlGsonTypeAdapterFactory();

        return factory;
    }
}

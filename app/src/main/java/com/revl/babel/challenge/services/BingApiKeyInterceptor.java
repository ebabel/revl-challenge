package com.revl.babel.challenge.services;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BingApiKeyInterceptor implements Interceptor {
    private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";
    private final String apiKey;

    public BingApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .header(API_KEY_HEADER_NAME, apiKey).build();
        return chain.proceed(request);
    }
}

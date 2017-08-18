package com.revl.babel.challenge;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revl.babel.challenge.services.BingApi;
import com.revl.babel.challenge.services.BingApiKeyInterceptor;
import com.revl.babel.challenge.services.RevlGsonTypeAdapterFactory;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RevlApplication extends Application {


    private static final String BASE_URL = "https://api.cognitive.microsoft.com/bing/v5.0/images/";
    private static final String API_KEY = "a6738e4c135542229c8e5c5d8a697c69";
    private static RevlApplication instance;
    private BingApi api;

    public static RevlApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        api = createRetrofit().create(BingApi.class);
    }

    @NonNull
    private static Retrofit createRetrofit() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new BingApiKeyInterceptor(API_KEY))
                .addNetworkInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(RevlGsonTypeAdapterFactory.create())
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory
                                .createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public BingApi getApi() {
        return api;
    }
}

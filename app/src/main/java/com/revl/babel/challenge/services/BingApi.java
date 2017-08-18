package com.revl.babel.challenge.services;

import com.revl.babel.challenge.model.Images;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BingApi {
    @GET("search")
    Single<Images> searchForImages(
            @Query("q") String keyword,
            @Query("offset") int offset,
            @Query("count") int count,
            @Query("mkt") String market,
            @Query("safeSearch") String safeSearch);
}



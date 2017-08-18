package com.revl.babel.challenge.services;

import com.revl.babel.challenge.model.Images;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

// https://msdn.microsoft.com/en-us/library/dn760791(v=bsynd.50).aspx
public interface BingApi {
    @GET("search")
    Single<Images> searchForImages(
            @Query("q") String keyword,
            @Query("offset") int offset,
            @Query("count") int count,
            @Query("mkt") String market,
            @Query("imageType") String imageType,
            @Query("safeSearch") String safeSearch);
}



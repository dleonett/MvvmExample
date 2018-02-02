package com.example.danielleonett.mvvmexample.data.remote;

import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.data.remote.response.AuthResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by daniel.leonett on 31/01/2018.
 */

public interface SpotifyApiService {

    @GET("search")
    Single<ArtistsResponse> search(@Query("q") String query,
                                   @Query("type") String type,
                                   @Query("market") String market,
                                   @Query("limit") int limit);

    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    Single<AuthResponse> auth(@Header("Authorization") String authorization,
                              @Field("grant_type") String grantType);

}
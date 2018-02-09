package com.example.danielleonett.mvvmexample.data.remote;

import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.data.remote.response.AuthResponse;
import com.example.danielleonett.mvvmexample.util.Constants;

import io.reactivex.Single;

/**
 * Created by daniel.leonett on 31/01/2018.
 */

public class SpotifyRemote {

    private static final String TYPE_ARTIST = "artist";

    private static SpotifyRemote instance;

    private SpotifyApiService apiServiceAuth;
    private SpotifyApiService apiService;

    public static SpotifyRemote getInstance() {
        if (instance == null) {
            instance = new SpotifyRemote();
        }
        return instance;
    }

    private SpotifyRemote() {
        initApiServiceAuth();
        initApiService();
    }

    private void initApiServiceAuth() {
        BearerInterceptor bearerInterceptor = new BearerInterceptor();

        apiServiceAuth = HttpClientGenerator.createClient(SpotifyApiService.class,
                Constants.Url.BASE_URL,
                bearerInterceptor);
    }

    private void initApiService() {
        apiService = HttpClientGenerator.createClient(SpotifyApiService.class,
                Constants.Url.BASE_URL);
    }

    public Single<AuthResponse> auth() {
        return apiService.auth("Basic " + Constants.CLIENT_ID,
                "client_credentials");
    }

    public Single<ArtistsResponse> searchArtists(String query) {
        return apiServiceAuth.search(query, TYPE_ARTIST, Constants.REGION, 20);
    }
}
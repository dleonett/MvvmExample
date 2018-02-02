package com.example.danielleonett.mvvmexample.data.remote;

import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.data.remote.response.AuthResponse;
import com.example.danielleonett.mvvmexample.util.Constants;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daniel.leonett on 31/01/2018.
 */

public class SpotifyRemote {

    private static final String TYPE_ARTIST = "artist";
    private static final String TYPE_ALBUM = "album";
    private static final String TYPE_TRACK = "track";

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
        // Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Bearer interceptor
        BearerInterceptor bearerInterceptor = new BearerInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(bearerInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiServiceAuth = retrofit.create(SpotifyApiService.class);
    }

    private void initApiService() {
        // Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(SpotifyApiService.class);
    }

    public Single<AuthResponse> auth() {
        return apiService.auth("Basic " + Constants.CLIENT_ID,
                "client_credentials")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ArtistsResponse> searchArtists(String query) {
        return apiServiceAuth.search(query, TYPE_ARTIST, Constants.REGION, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ArtistsResponse> searchAlbums(String query) {
        return apiServiceAuth.search(query, TYPE_ALBUM, Constants.REGION, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ArtistsResponse> searchTracks(String query) {
        return apiServiceAuth.search(query, TYPE_TRACK, Constants.REGION, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
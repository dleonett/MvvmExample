package com.example.danielleonett.mvvmexample.data;

import com.example.danielleonett.mvvmexample.data.local.PreferencesManager;
import com.example.danielleonett.mvvmexample.data.remote.SpotifyRemote;
import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.data.remote.response.AuthResponse;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import retrofit2.HttpException;

/**
 * Created by daniel.leonett on 31/01/2018.
 */

public class SpotifyRepository {

    // Constants
    public static final String TAG = SpotifyRepository.class.getSimpleName();

    // Fields
    private static SpotifyRepository instance;
    private SpotifyRemote spotifyRemote;
    private PreferencesManager preferencesManager;

    public static SpotifyRepository getInstance() {
        return getInstance(SpotifyRemote.getInstance(), PreferencesManager.getInstance());
    }

    public static SpotifyRepository getInstance(SpotifyRemote spotifyRemote,
                                                PreferencesManager preferencesManager) {
        if (instance == null) {
            instance = new SpotifyRepository(spotifyRemote, preferencesManager);
        }
        return instance;
    }

    public SpotifyRepository(SpotifyRemote spotifyRemote, PreferencesManager preferencesManager) {
        this.spotifyRemote = spotifyRemote;
        this.preferencesManager = preferencesManager;
    }

    private Single<ArtistsResponse> authAndSearchArtists(final String query) {
        return spotifyRemote.auth()
                .flatMap(new Function<AuthResponse, SingleSource<? extends ArtistsResponse>>() {
                    @Override
                    public SingleSource<? extends ArtistsResponse> apply(AuthResponse authResponse) throws Exception {
                        saveAccessTokenFromAuthResponse(authResponse);

                        return spotifyRemote.searchArtists(query);
                    }
                });
    }

    private void saveAccessTokenFromAuthResponse(AuthResponse authResponse) {
        String accessToken = authResponse.getAccessToken();
        preferencesManager.storeAccessToken(accessToken);
    }

    private Single<ArtistsResponse> onlySearchArtists(String query) {
        return spotifyRemote.searchArtists(query)
                .onErrorResumeNext(new Function<Throwable, SingleSource<? extends ArtistsResponse>>() {
                    @Override
                    public SingleSource<? extends ArtistsResponse> apply(Throwable throwable) throws Exception {
                        if (throwable instanceof HttpException) {
                            HttpException exception = (HttpException) throwable;
                            if (exception.code() == 401) {
                                return Single.just(new ArtistsResponse());
                            }
                        }
                        return Single.error(throwable);
                    }
                });
    }

    public Single<ArtistsResponse> searchArtists(String query) {
        return Single.concat(
                onlySearchArtists(query),
                authAndSearchArtists(query))
                .filter(new Predicate<ArtistsResponse>() {
                    @Override
                    public boolean test(ArtistsResponse artistsResponse) throws Exception {
                        return artistsResponse.getArtists() != null;
                    }
                })
                .firstOrError();
    }

}

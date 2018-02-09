package com.example.danielleonett.mvvmexample.data;

import com.example.danielleonett.mvvmexample.data.local.PreferencesManager;
import com.example.danielleonett.mvvmexample.data.remote.SpotifyRemote;
import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.data.remote.response.AuthResponse;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by daniel.leonett on 31/01/2018.
 */

public class SpotifyRepository {

    // Constants
    public static final String TAG = SpotifyRepository.class.getSimpleName();

    // Fields
    private static SpotifyRepository instance;
    private SpotifyRemote spotifyRemote;

    public static SpotifyRepository getInstance() {
        if (instance == null) {
            instance = new SpotifyRepository();
        }
        return instance;
    }

    private SpotifyRepository() {
        spotifyRemote = SpotifyRemote.getInstance();
    }

    private Single<ArtistsResponse> authAndSearchArtists(final String query) {
        return spotifyRemote.auth()
                .flatMap(new Function<AuthResponse, SingleSource<? extends ArtistsResponse>>() {
                    @Override
                    public SingleSource<? extends ArtistsResponse> apply(AuthResponse authResponse) throws Exception {
                        PreferencesManager.getInstance()
                                .storeAccessToken(authResponse.getAccessToken());

                        return spotifyRemote.searchArtists(query);
                    }
                });
    }

    public Single<ArtistsResponse> searchArtists(String query) {
        return Single.concat(
                spotifyRemote.searchArtists(query)
                        .onErrorReturnItem(new ArtistsResponse()),
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

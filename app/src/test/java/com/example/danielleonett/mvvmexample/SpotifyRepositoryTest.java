package com.example.danielleonett.mvvmexample;

import com.example.danielleonett.mvvmexample.data.SpotifyRepository;
import com.example.danielleonett.mvvmexample.data.local.PreferencesManager;
import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.data.remote.SpotifyRemote;
import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.data.remote.response.AuthResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by daniel.leonett on 12/02/2018.
 */

public class SpotifyRepositoryTest {

    @Mock
    private SpotifyRemote spotifyRemote;

    @Mock
    private PreferencesManager preferencesManager;

    private SpotifyRepository spotifyRepository;

    private ArtistsResponse artistsResponse;

    private AuthResponse authResponse;

    private TestObserver<ArtistsResponse> testObserver;

    private String query;

    private String accessToken;

    private HttpException notAuthenticatedException;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        spotifyRepository = new SpotifyRepository(spotifyRemote, preferencesManager);

        testObserver = new TestObserver<>();

        List<Artist> artistList = new ArrayList<>();
        artistList.add(new Artist("1", "Ed Sheeran"));
        artistList.add(new Artist("2", "Imagine Dragons"));
        artistList.add(new Artist("3", "Morat"));
        artistList.add(new Artist("4", "Coldplay"));

        artistsResponse = new ArtistsResponse(artistList);

        accessToken = "myAccessToken";
        authResponse = new AuthResponse(accessToken);

        notAuthenticatedException =
                new HttpException(Response.error(401, ResponseBody.create(null, "")));

        query = "foo";
    }

    @Test
    public void getArtistsAuthenticated_ArtistsSuccess() {
        when(spotifyRemote.searchArtists(query))
                .thenReturn(Single.just(artistsResponse));

        when(spotifyRemote.auth())
                .thenReturn(Single.just(new AuthResponse()));

        spotifyRepository.searchArtists(query).subscribe(testObserver);

        testObserver.assertNoErrors()
                .assertValue(new Predicate<ArtistsResponse>() {
                    @Override
                    public boolean test(ArtistsResponse artistsResponse) throws Exception {
                        return artistsResponse.getArtists() != null;
                    }
                });
    }

    @Test
    public void getArtistsNotAuthenticated_AuthSuccessArtistsSuccess() {
        when(spotifyRemote.searchArtists(query))
                .thenReturn(Single.<ArtistsResponse>error(notAuthenticatedException))
                .thenReturn(Single.just(artistsResponse));

        when(spotifyRemote.auth())
                .thenReturn(Single.just(authResponse));

        spotifyRepository.searchArtists(query).subscribe(testObserver);

        testObserver.assertNoErrors()
                .assertValue(new Predicate<ArtistsResponse>() {
                    @Override
                    public boolean test(ArtistsResponse artistsResponse) throws Exception {
                        return artistsResponse.getArtists() != null;
                    }
                });

        verify(preferencesManager).storeAccessToken(accessToken);
    }

    @Test
    public void getArtistsAuthenticated_ArtistsError() {
        Throwable throwable = new RuntimeException();

        when(spotifyRemote.searchArtists(query))
                .thenReturn(Single.<ArtistsResponse>error(throwable));

        when(spotifyRemote.auth())
                .thenReturn(Single.just(new AuthResponse()));

        spotifyRepository.searchArtists(query).subscribe(testObserver);

        testObserver.assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return throwable instanceof RuntimeException;
            }
        });

        verify(spotifyRemote).auth();
    }

    @Test
    public void getArtistsNotAuthenticated_AuthError() {
        when(spotifyRemote.searchArtists(query))
                .thenReturn(Single.<ArtistsResponse>error(notAuthenticatedException));

        when(spotifyRemote.auth())
                .thenReturn(Single.<AuthResponse>error(new RuntimeException("Error authenticating")));

        spotifyRepository.searchArtists(query).subscribe(testObserver);

        testObserver.assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
                return throwable instanceof RuntimeException;
            }
        });
    }

    @Test
    public void getArtistsNotAuthenticated_AuthSuccessArtistsError() {
        when(spotifyRemote.searchArtists(query))
                .thenReturn(Single.<ArtistsResponse>error(notAuthenticatedException))
                .thenReturn(Single.<ArtistsResponse>error(new RuntimeException("Error getting artists")));

        when(spotifyRemote.auth())
                .thenReturn(Single.just(authResponse));

        spotifyRepository.searchArtists(query).subscribe(testObserver);

        testObserver.assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
                return throwable instanceof RuntimeException;
            }
        });
    }

}

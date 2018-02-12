package com.example.danielleonett.mvvmexample;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.danielleonett.mvvmexample.data.SpotifyRepository;
import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.data.model.UiStateModel;
import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.ui.artists.ArtistsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by daniel.leonett on 6/02/2018.
 */

public class ArtistsViewModelTest {

    @Mock
    private SpotifyRepository spotifyRepository;

    @Mock
    private Observer<UiStateModel<List<Artist>>> uiStateModelObserver;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private ArtistsViewModel artistsViewModel;

    private List<Artist> artistList;

    private TestScheduler testScheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        testScheduler = new TestScheduler();

        artistsViewModel = new ArtistsViewModel(spotifyRepository, testScheduler, testScheduler);

        artistList = new ArrayList<>();
        artistList.add(new Artist("1", "Ed Sheeran"));
        artistList.add(new Artist("2", "Imagine Dragons"));
        artistList.add(new Artist("3", "Morat"));
        artistList.add(new Artist("4", "Coldplay"));
    }

    @Test
    public void searchArtistsLoading() {
        String query = "foo";

        doReturn(Single.never())
                .when(spotifyRepository).searchArtists(query);

        artistsViewModel.getArtists().observe(TestUtils.TEST_OBSERVER, uiStateModelObserver);

        artistsViewModel.searchArtists(query);

        testScheduler.triggerActions();

        verify(uiStateModelObserver)
                .onChanged(argThat(new ArgumentMatcher<UiStateModel<List<Artist>>>() {
                    @Override
                    public boolean matches(UiStateModel<List<Artist>> argument) {
                        return argument.isInProgress();
                    }
                }));
    }

    @Test
    public void searchArtistsSuccess() {
        String query = "foo";

        doReturn(Single.just(new ArtistsResponse(artistList)))
                .when(spotifyRepository).searchArtists(query);

        artistsViewModel.getArtists().observe(TestUtils.TEST_OBSERVER, uiStateModelObserver);

        artistsViewModel.searchArtists(query);

        testScheduler.triggerActions();

        verify(uiStateModelObserver)
                .onChanged(argThat(new ArgumentMatcher<UiStateModel<List<Artist>>>() {
                    @Override
                    public boolean matches(UiStateModel<List<Artist>> argument) {
                        return argument.isInProgress();
                    }
                }));
        verify(uiStateModelObserver)
                .onChanged(argThat(new ArgumentMatcher<UiStateModel<List<Artist>>>() {
                    @Override
                    public boolean matches(UiStateModel<List<Artist>> argument) {
                        return argument.isSuccess();
                    }
                }));
    }

    @Test
    public void searchArtistsError() {
        String query = "foo";
        String errorMessage = "bar";

        doReturn(Single.error(new RuntimeException(errorMessage)))
                .when(spotifyRepository).searchArtists(query);

        artistsViewModel.getArtists().observe(TestUtils.TEST_OBSERVER, uiStateModelObserver);

        artistsViewModel.searchArtists(query);

        testScheduler.triggerActions();

        verify(uiStateModelObserver)
                .onChanged(argThat(new ArgumentMatcher<UiStateModel<List<Artist>>>() {
                    @Override
                    public boolean matches(UiStateModel<List<Artist>> argument) {
                        return argument.isInProgress();
                    }
                }));
        verify(uiStateModelObserver)
                .onChanged(argThat(new ArgumentMatcher<UiStateModel<List<Artist>>>() {
                    @Override
                    public boolean matches(UiStateModel<List<Artist>> argument) {
                        return argument.isError();
                    }
                }));
    }
}

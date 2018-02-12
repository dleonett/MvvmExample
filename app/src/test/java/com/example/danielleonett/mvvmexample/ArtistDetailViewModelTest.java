package com.example.danielleonett.mvvmexample;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.data.model.UiStateModel;
import com.example.danielleonett.mvvmexample.ui.artists.ArtistsViewModel;
import com.example.danielleonett.mvvmexample.ui.artists.detail.ArtistDetailViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;

/**
 * Created by daniel.leonett on 12/02/2018.
 */

public class ArtistDetailViewModelTest {

    @Mock
    private Observer<Artist> uiStateModelObserver;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private ArtistDetailViewModel artistDetailViewModel;

    private Artist artist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        artistDetailViewModel = new ArtistDetailViewModel();

        artist = new Artist("1", "Ed Sheeran");
    }

    @Test
    public void setArtist() {
        artistDetailViewModel.getArtist().observe(TestUtils.TEST_OBSERVER, uiStateModelObserver);

        artistDetailViewModel.setArtist(artist);

        verify(uiStateModelObserver).onChanged(artist);
    }

}

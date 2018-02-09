package com.example.danielleonett.mvvmexample.ui.artists;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.danielleonett.mvvmexample.data.SpotifyRepository;

import io.reactivex.Scheduler;

public class ArtistsViewModelFactory implements ViewModelProvider.Factory {

    private final SpotifyRepository spotifyRepository;
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    public ArtistsViewModelFactory(SpotifyRepository spotifyRepository,
                                   Scheduler ioScheduler,
                                   Scheduler uiScheduler) {
        this.spotifyRepository = spotifyRepository;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArtistsViewModel.class)) {
            return (T) new ArtistsViewModel(spotifyRepository, ioScheduler, uiScheduler);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
package com.example.danielleonett.mvvmexample.ui.artists;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.danielleonett.mvvmexample.data.SpotifyRepository;
import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.data.model.UiStateModel;
import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by daniel.leonett on 2/02/2018.
 */

public class ArtistsViewModel extends BaseViewModel {

    // Constants
    public static final String TAG = ArtistsViewModel.class.getSimpleName();

    // Fields
    private SpotifyRepository spotifyRepository;
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;
    private MutableLiveData<UiStateModel<List<Artist>>> artistsStateLiveData;

    public ArtistsViewModel(SpotifyRepository spotifyRepository, Scheduler ioScheduler, Scheduler uiScheduler) {
        this.spotifyRepository = spotifyRepository;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        artistsStateLiveData = new MutableLiveData<>();
    }

    public LiveData<UiStateModel<List<Artist>>> getArtists() {
        return artistsStateLiveData;
    }

    public void searchArtists(String query) {
        Disposable disposable =
                spotifyRepository
                        .searchArtists(query)
                        .subscribeOn(ioScheduler)
                        .observeOn(uiScheduler)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                postLoading();
                            }
                        })
                        .subscribe(new Consumer<ArtistsResponse>() {
                            @Override
                            public void accept(ArtistsResponse artistsResponse) throws Exception {
                                postOnSuccess(artistsResponse);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                postOnError(throwable);
                            }
                        });

        getCompositeDisposable().add(disposable);
    }

    private void postLoading() {
        artistsStateLiveData
                .setValue(UiStateModel.<List<Artist>>loading());
    }

    private void postOnSuccess(ArtistsResponse artistsResponse) {
        List<Artist> artistList;

        if (artistsResponse.getArtists() == null) {
            artistList = new ArrayList<>();
        } else {
            artistList = artistsResponse.getArtists().getItems();
        }

        artistsStateLiveData
                .setValue(UiStateModel.success(artistList));
    }

    private void postOnError(Throwable throwable) {
        artistsStateLiveData
                .setValue(UiStateModel
                        .<List<Artist>>failure("An error occurred."));
    }

}

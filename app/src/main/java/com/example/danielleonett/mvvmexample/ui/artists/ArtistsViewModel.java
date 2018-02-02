package com.example.danielleonett.mvvmexample.ui.artists;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.danielleonett.mvvmexample.data.SpotifyRepository;
import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.data.model.UiStateModel;
import com.example.danielleonett.mvvmexample.data.remote.response.ArtistsResponse;
import com.example.danielleonett.mvvmexample.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by daniel.leonett on 2/02/2018.
 */

public class ArtistsViewModel extends BaseViewModel {

    public static final String TAG = ArtistsViewModel.class.getSimpleName();

    private MutableLiveData<UiStateModel<List<Artist>>> artistsStateLiveData;

    public ArtistsViewModel() {
        artistsStateLiveData = new MutableLiveData<>();
    }

    public LiveData<UiStateModel<List<Artist>>> getArtists() {
        return artistsStateLiveData;
    }

    public void searchArtists(String query) {
        Disposable disposable =
                SpotifyRepository.getInstance()
                        .searchArtists(query)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                artistsStateLiveData
                                        .postValue(UiStateModel.<List<Artist>>loading());
                            }
                        })
                        .subscribe(new Consumer<ArtistsResponse>() {
                            @Override
                            public void accept(ArtistsResponse artistsResponse) throws Exception {
                                if (artistsResponse.getArtists() == null) return;

                                artistsStateLiveData
                                        .postValue(UiStateModel
                                                .success(artistsResponse.getArtists().getItems()));
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                artistsStateLiveData
                                        .postValue(UiStateModel
                                                .<List<Artist>>failure("An error occurred."));
                            }
                        });

        getCompositeDisposable().add(disposable);
    }

}

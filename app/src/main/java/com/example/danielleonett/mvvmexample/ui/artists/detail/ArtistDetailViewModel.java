package com.example.danielleonett.mvvmexample.ui.artists.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.ui.base.BaseViewModel;

/**
 * Created by daniel.leonett on 2/02/2018.
 */

public class ArtistDetailViewModel extends BaseViewModel {

    public static final String TAG = ArtistDetailViewModel.class.getSimpleName();

    private MutableLiveData<Artist> artistLiveData;

    private Artist artist;

    public ArtistDetailViewModel() {
        artistLiveData = new MutableLiveData<>();
    }

    public LiveData<Artist> getArtist() {
        return artistLiveData;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        artistLiveData.setValue(artist);
    }

}

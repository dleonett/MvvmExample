package com.example.danielleonett.mvvmexample.ui.base;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by daniel.leonett on 2/02/2018.
 */

public abstract class BaseViewModel extends ViewModel {

    public static final String TAG = BaseViewModel.class.getSimpleName();

    private CompositeDisposable compositeDisposable;

    public BaseViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared()");
        compositeDisposable.clear();
    }
}

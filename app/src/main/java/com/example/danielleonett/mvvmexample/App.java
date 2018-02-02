package com.example.danielleonett.mvvmexample;

import android.app.Application;

import com.example.danielleonett.mvvmexample.data.local.PreferencesManager;

/**
 * Created by daniel.leonett on 1/02/2018.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesManager.init(this);
    }

}

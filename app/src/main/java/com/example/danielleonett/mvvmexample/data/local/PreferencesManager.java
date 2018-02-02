package com.example.danielleonett.mvvmexample.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    // Constants
    public static final String TAG = PreferencesManager.class.getSimpleName();
    private static final String PREF_NAME = "MvvmExamplePrefs";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    // Fields
    private static PreferencesManager instance;
    private SharedPreferences preferences;

    public static PreferencesManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new PreferencesManager(context);
    }

    private PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String retrieveAccessToken() {
        return preferences.getString(ACCESS_TOKEN, null);
    }

    public void storeAccessToken(String token) {
        preferences.edit()
                .putString(ACCESS_TOKEN, token)
                .apply();
    }

}
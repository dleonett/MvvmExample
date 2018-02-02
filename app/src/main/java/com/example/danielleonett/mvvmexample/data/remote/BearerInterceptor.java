package com.example.danielleonett.mvvmexample.data.remote;

import android.support.annotation.NonNull;

import com.example.danielleonett.mvvmexample.data.local.PreferencesManager;
import com.example.danielleonett.mvvmexample.util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BearerInterceptor implements Interceptor {

    // Constants
    private static final String TAG = BearerInterceptor.class.getSimpleName();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder requestBuilder;

        requestBuilder = chain.request().newBuilder().addHeader(
                "Authorization",
                "Bearer " + PreferencesManager.getInstance().retrieveAccessToken());
        return chain.proceed(requestBuilder.build());
    }

}
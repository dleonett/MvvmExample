package com.example.danielleonett.mvvmexample.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniel.leonett on 1/02/2018.
 */

public class AuthResponse {

    @SerializedName("access_token")
    private String accessToken;

    public AuthResponse() {
    }

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}

package com.github.ipan97.kpexpress.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ipan Taupik Rahman.
 */
public class LoginResponse {
    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

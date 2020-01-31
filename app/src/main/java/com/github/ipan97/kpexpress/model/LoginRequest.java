package com.github.ipan97.kpexpress.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Ipan Taupik Rahman.
 */
public class LoginRequest implements Serializable {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

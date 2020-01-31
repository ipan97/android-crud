package com.github.ipan97.kpexpress.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Ipan Taupik Rahman.
 */
public class Meta implements Serializable {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;
}

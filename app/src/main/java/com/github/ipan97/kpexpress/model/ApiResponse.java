package com.github.ipan97.kpexpress.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ipan Taupik Rahman.
 */
public class ApiResponse implements Serializable {
    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    private Object data;

    @SerializedName("errors")
    private List<Object> errors;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }
}

package com.github.ipan97.kpexpress.network;

import com.github.ipan97.kpexpress.model.ApiResponse;
import com.github.ipan97.kpexpress.model.LoginRequest;
import com.github.ipan97.kpexpress.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Ipan Taupik Rahman.
 */
public interface ApiEndpoint {

    @POST("api/auth/login")
    Call<ApiResponse> login(@Body LoginRequest request);

    @POST("api/auth/register")
    Call<ApiResponse> register(@Body RegisterRequest request);
}

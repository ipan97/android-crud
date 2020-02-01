package com.github.ipan97.kpexpress.network;

import com.github.ipan97.kpexpress.model.ApiResponse;
import com.github.ipan97.kpexpress.model.LoginRequest;
import com.github.ipan97.kpexpress.model.RegisterRequest;

import java.math.BigDecimal;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author Ipan Taupik Rahman.
 */
public interface ApiEndpoint {

    @POST("api/auth/login")
    Call<ApiResponse> login(@Body LoginRequest request);

    @POST("api/auth/register")
    Call<ApiResponse> register(@Body RegisterRequest request);

    @GET("api/products")
    Call<ApiResponse> getProducts();

    @Multipart
    @POST("api/products")
    Call<ApiResponse> addProduct(
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part photo
    );
}

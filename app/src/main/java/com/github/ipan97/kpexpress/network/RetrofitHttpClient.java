package com.github.ipan97.kpexpress.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ipan Taupik Rahman.
 */
public class RetrofitHttpClient {

    private static final String BASE_URL = "http://192.168.1.10/php-native-rest-api/";

    public static ApiEndpoint client() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiEndpoint.class);
    }
}

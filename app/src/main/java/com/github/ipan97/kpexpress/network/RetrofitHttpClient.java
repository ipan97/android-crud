package com.github.ipan97.kpexpress.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * @author Ipan Taupik Rahman.
 */
public class RetrofitHttpClient {

    public static final String BASE_URL = "http://192.168.43.36/php-native-rest-api/";
    public static final String BASE_IMAGE_URL = BASE_URL + "images/";

    public static ApiEndpoint client() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(ApiEndpoint.class);
    }
}

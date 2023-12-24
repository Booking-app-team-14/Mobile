package com.example.bookingapptim14.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    private static final String SERVICE_API_PATH = "http://192.168.232.19:8080";
    public static Retrofit retrofit = createRetrofitClient();

    private static Retrofit createRetrofitClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(SERVICE_API_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();
    }
}

package com.example.bookingapptim14.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.interfaces.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {
    public static final String SERVICE_API_PATH = BuildConfig.SERVER_IP;
    public static SharedPreferences preferences = null;

    public static void setPreferences(SharedPreferences preferences) {
        ClientUtils.preferences = preferences;

        retrofit = new Retrofit.Builder()
                .baseUrl(SERVICE_API_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    /*
     * Ovo ce nam sluziti za debug, da vidimo da li zahtevi i odgovori idu
     * odnosno dolaze i kako izgledaju.
     * */
    public static OkHttpClient getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    /*
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */
    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                    (json, typeOfT, context) -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                    (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .create();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient())
            .build();

    public static String getError(Response<?> response, String defaultValue) {
        String error = defaultValue;
        if (response.headers().names().contains("Error-Type")) {
            try {
                error = response.errorBody().string();
            } catch (IOException | NullPointerException e) {
                Log.e("ClientUtils", e.getMessage(), e);
            }
        }
        return error;
    }

    /*
     * Definisemo konkretne instance servisa na internetu sa kojima
     * vrsimo komunikaciju
     * */
    public static ApiService apiService = retrofit.create(ApiService.class);
}

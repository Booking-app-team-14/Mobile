package com.example.bookingapptim14.interfaces;

import com.example.bookingapptim14.dtos.JwtAuthenticationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("/api/login")
    Call<String> login(@Body JwtAuthenticationRequest request);
}


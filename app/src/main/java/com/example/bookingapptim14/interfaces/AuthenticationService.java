package com.example.bookingapptim14.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.bookingapptim14.models.JwtAuthenticationRequest;

public interface AuthenticationService {

    @POST("api/login")
    Call<String> createAuthenticationToken(@Body JwtAuthenticationRequest authenticationRequest);
}



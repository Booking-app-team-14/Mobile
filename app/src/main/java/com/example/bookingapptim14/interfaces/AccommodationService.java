package com.example.bookingapptim14.interfaces;

import com.example.bookingapptim14.models.SearchAccommodation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccommodationService {

    @GET("accommodations/sort/rating/desc")
    Call<List<SearchAccommodation>> getAll();

    @GET("accommodations/{id}")
    Call<List<SearchAccommodation>> getById(@Path("id") Long id);

    @GET("/search")
    Call<List<SearchAccommodation>> searchAccommodations(
            @Query("searchTerm") String searchTerm
    );

    @GET("/sort/price/asc")
    Call<List<SearchAccommodation>> getAllAccommodationsSortedByPriceAsc();

    @GET("/sort/price/desc")
    Call<List<SearchAccommodation>> getAllAccommodationsSortedByPriceDesc();

    @GET("/sort/rating/desc")
    Call<List<SearchAccommodation>> getAllAccommodationsSortedByRatingDesc();

    @GET("/sort/rating/asc")
    Call<List<SearchAccommodation>> getAllAccommodationsSortedByRatingAsc();

}


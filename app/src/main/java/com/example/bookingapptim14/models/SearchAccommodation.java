package com.example.bookingapptim14.models;

import android.health.connect.datatypes.StepsCadenceRecord;

import com.example.bookingapptim14.enums.AccommodationType;

public class SearchAccommodation {

    private Long id;
    private String name;
    private String description;
    private AccommodationType accommodationType;
    private String image;
    private Double rating;
    private Integer maxNumberOfGuests;
    private Double pricePerNight;
    private boolean approved;

    public SearchAccommodation(Long id, String name, String description, AccommodationType accommodationType,
                                  String image, Double rating, Integer maxNumberOfGuests, Double pricePerNight, boolean approved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accommodationType = accommodationType;
        this.image = image;
        this.rating = rating;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.pricePerNight = pricePerNight;
        this.approved = approved;
    }

    private void setId(Long id){
        this.id = id;
    }
    private Long getId(){
        return this.id;
    }

    private void setName(String name){
        this.name = name;
    }
    private String getName(){
        return this.name;
    }
    private void setDescription(String description){
        this.description = description;
    }
    private String getDescription(){
        return this.description;
    }
    private void setAccommodationType(AccommodationType accommodationType){
        this.accommodationType = accommodationType;
    }
    private AccommodationType getAccommodationType(){
        return this.accommodationType;
    }
    private void setImage(String image){
        this.image = image;
    }
    private String getImage(){
        return this.image;
    }

    private void setRating(){
        this.rating = rating;
    }
    private Double getRating(){
        return this.rating;
    }

    private void setMaxNumberOfGuests(Integer maxNumberOfGuests){
        this.maxNumberOfGuests = maxNumberOfGuests;
    }
    private Integer getMaxNumberOfGuests(){
        return this.maxNumberOfGuests;
    }
    private void setPricePerNight(Double pricePerNight){
        this.pricePerNight = pricePerNight;
    }
    private Double getPricePerNight(){
        return this.pricePerNight;
    }

    private void setApproved(boolean approved){
        this.approved = approved;
    }
    private boolean isApproved(){
        return this.approved;
    }
}

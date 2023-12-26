package com.example.bookingapptim14.models;

import com.example.bookingapptim14.enums.AccommodationType;

import java.io.Serializable;
import java.util.Set;

public class SearchAccommodation implements Serializable {

    private Long id;
    private String name;
    private String description;
    private AccommodationType accommodationType;
    private String image;

    private Set<Amenity> amenities;
    private Location location;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Double rating;
    private Double pricePerNight;
    private boolean approved;

    public SearchAccommodation(Long id, String name, String description, AccommodationType accommodationType, Location location,
                                  String image, Double rating,Integer minNumberOfGuests, Integer maxNumberOfGuests, Double pricePerNight, Set<Amenity> amenities, boolean approved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accommodationType = accommodationType;
        this.location = location;
        this.image = image;
        this.rating = rating;
        this.minNumberOfGuests = minNumberOfGuests;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.pricePerNight = pricePerNight;
        this.amenities = amenities;
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
    public String getName(){
        return this.name;
    }
    private void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
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
    public String getImage(){
        return this.image;
    }

    private void setRating(){
        this.rating = rating;
    }
    public Double getRating(){
        return this.rating;
    }

    private void setMaxNumberOfGuests(Integer maxNumberOfGuests){
        this.maxNumberOfGuests = maxNumberOfGuests;
    }
    public Integer getMaxNumberOfGuests(){
        return this.maxNumberOfGuests;
    }
    private void setMinNumberOfGuests(Integer minNumberOfGuests){
        this.minNumberOfGuests = minNumberOfGuests;
    }
    public Integer getMinNumberOfGuests(){
        return this.minNumberOfGuests;
    }
    private void setPricePerNight(Double pricePerNight){
        this.pricePerNight = pricePerNight;
    }
    public Double getPricePerNight(){
        return this.pricePerNight;
    }

    private void setApproved(boolean approved){
        this.approved = approved;
    }
    private boolean isApproved(){
        return this.approved;
    }
}

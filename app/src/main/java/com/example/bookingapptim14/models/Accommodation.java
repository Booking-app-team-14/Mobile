package com.example.bookingapptim14.models;

import com.example.bookingapptim14.enums.AccommodationType;

import java.util.Set;

public class Accommodation {


    private Long id;
    private String name;
    private String description;
    private Location location;
    private AccommodationType type;
    private Set<String> images;
    private Set<Amenity> amenities;
    private Double rating;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Set<Availability> availability;
    private Double pricePerNight;
    private boolean pricePerGuest;
    private Integer cancellationDeadline;
    private Long owner_Id;

    public Accommodation(
            Long id, String name, String description, Location location,
            AccommodationType type, Set<String> images, Set<Amenity> amenities,
            Double rating, Integer minNumberOfGuests, Integer maxNumberOfGuests,
            Set<Availability> availability, Double pricePerNight,
            boolean pricePerGuest, Integer cancellationDeadline, Long owner_Id
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.images = images;
        this.amenities = amenities;
        this.rating = rating;
        this.minNumberOfGuests = minNumberOfGuests;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.availability = availability;
        this.pricePerNight = pricePerNight;
        this.pricePerGuest = pricePerGuest;
        this.cancellationDeadline = cancellationDeadline;
        this.owner_Id = owner_Id;
    }

    public Accommodation() {
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
    private void setRating(Double rating){
        this.rating = rating;
    }
    private Double getRating(){
        return this.rating;
    }
    private void setAmenities(Set<Amenity> amenities){
        this.amenities = amenities;
    }
    private Set<Amenity> getAmenities(){
        return this.amenities;
    }

    private void setLocation(Location location){
        this.location = location;
    }
    private Location getLocation(){
        return this.location;
    }
    private void setAvailability(Set<Availability> availability){
        this.availability = availability;
    }
    private Set<Availability> getAvailability(){
        return this.availability;
    }

    private void setType(AccommodationType type){
        this.type = type;
    }
    private AccommodationType getType(){
        return this.type;
    }
    private void setImages(Set<String> images){
        this.images = images;
    }
    private Set<String> getImages(){
        return this.images;
    }

    private void setMaxNumberOfGuests(Integer maxNumberOfGuests){
        this.maxNumberOfGuests = maxNumberOfGuests;
    }
    private Integer getMaxNumberOfGuests(){
        return this.maxNumberOfGuests;
    }

    private void setMinNumberOfGuests(Integer minNumberOfGuests){
        this.minNumberOfGuests = minNumberOfGuests;
    }
    private Integer getMinNumberOfGuests(){
        return this.minNumberOfGuests;
    }
    private void setPricePerNight(Double pricePerNight){
        this.pricePerNight = pricePerNight;
    }
    private Double getPricePerNight(){
        return this.pricePerNight;
    }

    private void setPricePerGuest(boolean pricePerGuest){
        this.pricePerGuest = pricePerGuest;
    }
    private boolean isPricePerGuest(){
        return this.pricePerGuest;
    }

    private  void  setCancellationDeadline(Integer cancellationDeadline){
        this.cancellationDeadline= cancellationDeadline;
    }
    private  Integer getCancellationDeadline(){
        return  this.cancellationDeadline;
    }
    private  void setOwner_Id(Long owner_Id){
        this.owner_Id = owner_Id;
    }
    private Long getOwner_Id(){
        return this.owner_Id;
    }
}
package com.example.bookingapptim14.models;

import com.example.bookingapptim14.enums.AccommodationType;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AmenityDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.LocationDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Accommodation {



    private Long id;

    private String name;

    private String description;

    private LocationDTO location;

    private AccommodationType type;

    private Set<String> images = new HashSet<>();

    private List<String> imageTypes = new ArrayList<>();

    private Set<String> imageBytes = new HashSet<>();

    private Set<AmenityDTO> amenities;

    private Double rating;

    private Integer minNumberOfGuests;

    private Integer maxNumberOfGuests;

    private Set<Availability> availability;

    private Double pricePerNight;
    private boolean pricePerGuest;
    private boolean automatic;

    private Integer cancellationDeadline;

    private Long owner_Id;

    public Accommodation(
            Long id, String name, String description, LocationDTO location,
            AccommodationType type, Set<String> images, Set<AmenityDTO> amenities,
            Double rating, Integer minNumberOfGuests, Integer maxNumberOfGuests,
            Set<Availability> availability, Double pricePerNight,
            boolean pricePerGuest,boolean automatic, Integer cancellationDeadline, Long owner_Id
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
        this.automatic = automatic;
        this.cancellationDeadline = cancellationDeadline;
        this.owner_Id=owner_Id;
    }

    public Accommodation() {
    }

    private void setId(Long id){
        this.id = id;
    }
    public Long getId(){
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
    private void setRating(Double rating){
        this.rating = rating;
    }
    public float getRating(){
        return this.rating.floatValue();
    }
    private void setAmenities(Set<AmenityDTO> amenities){
        this.amenities = amenities;
    }
    private Set<AmenityDTO> getAmenities(){
        return this.amenities;
    }

    private void setLocation(LocationDTO location){
        this.location = location;
    }
    private LocationDTO getLocation(){
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
    public AccommodationType getType(){
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
    public Integer getMaxNumberOfGuests(){
        return this.maxNumberOfGuests;
    }

    private void setMinNumberOfGuests(Integer minNumberOfGuests){
        this.minNumberOfGuests = minNumberOfGuests;
    }

    public void setImageBytes(Set<String> bytes){
        this.images = bytes;
    }
    public Set<String> getImageBytes(){
        return this.imageBytes;
    }
    public Set<Availability> getAvailabilities(){
        return this.availability;
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

    public String getCity(){
        return this.location.getCity();
    }

    public String getCountry(){
        return this.location.getCountry();
    }
    public String getAddress(){
        return this.location.getAddress();
    }
}

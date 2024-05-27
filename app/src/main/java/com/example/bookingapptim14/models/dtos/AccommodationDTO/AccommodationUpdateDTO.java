package com.example.bookingapptim14.models.dtos.AccommodationDTO;

import com.example.bookingapptim14.models.dtos.Image;

import java.util.Set;

public class AccommodationUpdateDTO {

    private Long id;
    private Set<Image> images;
    private String name;
    private String description;
    private String type;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Set<Long> amenities;
    private LocationDTO location;
    private boolean pricePerGuest;
    private Double defaultPrice;
    private Set<UpdateAvailabilityDTO> availability;
    private Integer cancellationDeadline;
    private String message;
    private boolean automaticHandling;

    public AccommodationUpdateDTO() { }

    public AccommodationUpdateDTO(Long id, Set<Image> images, String name, String description, String type, Integer minNumberOfGuests, Integer maxNumberOfGuests, Set<Long> amenities, LocationDTO location, boolean pricePerGuest, Double defaultPrice, Set<UpdateAvailabilityDTO> availability, Integer cancellationDeadline, String message, boolean automaticHandling) {
        this.id = id;
        this.images = images;
        this.name = name;
        this.description = description;
        this.type = type;
        this.minNumberOfGuests = minNumberOfGuests;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.amenities = amenities;
        this.location = location;
        this.pricePerGuest = pricePerGuest;
        this.defaultPrice = defaultPrice;
        this.availability = availability;
        this.cancellationDeadline = cancellationDeadline;
        this.message = message;
        this.automaticHandling = automaticHandling;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMinNumberOfGuests() {
        return minNumberOfGuests;
    }

    public void setMinNumberOfGuests(Integer minNumberOfGuests) {
        this.minNumberOfGuests = minNumberOfGuests;
    }

    public Integer getMaxNumberOfGuests() {
        return maxNumberOfGuests;
    }

    public void setMaxNumberOfGuests(Integer maxNumberOfGuests) {
        this.maxNumberOfGuests = maxNumberOfGuests;
    }

    public Set<Long> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Long> amenities) {
        this.amenities = amenities;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public boolean isPricePerGuest() {
        return pricePerGuest;
    }

    public void setPricePerGuest(boolean pricePerGuest) {
        this.pricePerGuest = pricePerGuest;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Set<UpdateAvailabilityDTO> getAvailability() {
        return availability;
    }

    public void setAvailability(Set<UpdateAvailabilityDTO> availability) {
        this.availability = availability;
    }

    public Integer getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(Integer cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAutomaticHandling() {
        return automaticHandling;
    }

    public void setAutomaticHandling(boolean automaticHandling) {
        this.automaticHandling = automaticHandling;
    }
}

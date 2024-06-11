package com.example.bookingapptim14.models;

public class SearchAccommodation {

    private Long id;
    private String name;
    private String type;
    private int stars;
    private int maxGuests;
    private String address;
    private double price;
    private String imageType;
    private String mainPictureBytes;

    public SearchAccommodation() {
    }

    public SearchAccommodation(Long id, String name, String type, int stars, int maxGuests, String address, double price, String imageType, String mainPictureBytes) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stars = stars;
        this.maxGuests = maxGuests;
        this.address = address;
        this.price = price;
        this.imageType = imageType;
        this.mainPictureBytes = mainPictureBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getMainPictureBytes() {
        return mainPictureBytes;
    }

    public void setMainPictureBytes(String mainPictureBytes) {
        this.mainPictureBytes = mainPictureBytes;
    }
}

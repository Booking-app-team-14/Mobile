package com.example.bookingapptim14.models.dtos.AccommodationDTO;


import com.example.bookingapptim14.enums.AccommodationType;

public class AccommodationReportDTO {

    private String accommodationName;

    private AccommodationType type;

    protected String imageType;

    protected String imageBytes;
    private Double rating;

    private Integer minNumberOfGuests;

    private Integer maxNumberOfGuests;

    private Double pricePerNight;

    private int numberOfReservations;

    private double totalProfit;

    public AccommodationReportDTO() {
    }

    public AccommodationReportDTO(String name, AccommodationType type, Double rating,Integer minGuests,Integer maxGuest,Double price,   Integer numberOfReservations, Integer totalProfit) {
        this.accommodationName = name;
        this.type = type;
        this.rating = rating;
        this.minNumberOfGuests = minGuests;
        this.maxNumberOfGuests = maxGuest;
        this.pricePerNight = price;
        this.numberOfReservations = numberOfReservations;
        this.totalProfit = totalProfit;
        this.imageBytes = "";
        this.imageType = "";
    }

    public void setAccommodationName(String name){
        this.accommodationName = name;
    }
     public String getAccommodationName(){
        return this.accommodationName;
     }

    public void setType(AccommodationType name){
        this.type = name;
    }
    public AccommodationType getType() {
        return type;
    }

    public void setRating(Double name){
        this.rating = name;
    }
    public float getRating(){
        Double rating1 = this.rating;
        return rating1.floatValue();
    } public void setMinNumberOfGuests(Integer name){
        this.minNumberOfGuests = name;
    }
    public Integer getMinNumberOfGuests(){
        return this.minNumberOfGuests;
    } public void setMaxNumberOfGuests(Integer name){
        this.maxNumberOfGuests = name;
    }
    public Integer getMaxNumberOfGuests(){
        return this.maxNumberOfGuests;
    } public void setPricePerNight(Double name){
        this.pricePerNight = name;
    }
    public Double getPricePerNight(){
        return this.pricePerNight;
    } public void setImageType(String name){
        this.imageType = name;
    }
    public String getImageType(){
        return this.imageType;
    } public void setImageBytes(String name){
        this.imageBytes = name;
    }
    public String getImageBytes(){
        return this.imageBytes;
    } public void setNumberOfReservations(Integer name){
        this.numberOfReservations = name;
    }
    public int getNumberOfReservations(){
        return this.numberOfReservations;
    } public void setTotalProfit(double name){
        this.totalProfit = name;
    }
    public double getTotalProfit(){
        return this.totalProfit;
    }


}

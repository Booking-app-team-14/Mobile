package com.example.bookingapptim14.models;

import java.io.Serializable;

public class Location implements Serializable {
    private Long id;
    private String country;
    private String city;
    private String address;
    public Location(Long id, String country, String city, String address) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    private void setId(Long id){
        this.id = id;
    }
    private Long getId(){
        return this.id;
    }

    private void setCountry(String country){
        this.city = city;
    }
    private String getCountry(){
        return this.country;
    }
    private void setCity(String city){
        this.city = city;
    }
    private String getCity(){
        return this.city;
    }
}

package com.example.bookingapptim14.models;

public class Amenity {
    private Long id;
    private String name;
    private String description;
    private String icon;

    public Amenity(Long id, String name, String description, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
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

    private void setIcon(String icon){
        this.icon = icon;
    }
    private String getIcon(){
        return this.icon;
    }

}

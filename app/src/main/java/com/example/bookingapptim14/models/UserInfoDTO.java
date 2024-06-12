package com.example.bookingapptim14.models;


public class UserInfoDTO {

    protected String firstName;

    protected String lastName;


    protected String username;

    protected String profilePictureType;

    protected String profilePictureBytes;

    public UserInfoDTO(){}


    public UserInfoDTO(String firstName, String lastName, String username, String profilePictureType, String profilePictureBytes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.profilePictureType = profilePictureType;
        this.profilePictureBytes = profilePictureBytes;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePictureType() {
        return profilePictureType;
    }

    public String getProfilePictureBytes() {
        return profilePictureBytes;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfilePictureType(String profilePictureType) {
        this.profilePictureType = profilePictureType;
    }

    public void setProfilePictureBytes(String profilePictureBytes) {
        this.profilePictureBytes = profilePictureBytes;
    }
}

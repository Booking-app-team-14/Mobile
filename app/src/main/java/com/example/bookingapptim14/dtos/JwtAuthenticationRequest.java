package com.example.bookingapptim14.dtos;

public class JwtAuthenticationRequest {
    private String username;
    private String password;

    // No-argument constructor
    public JwtAuthenticationRequest() {
    }

    // Parameterized constructor
    public JwtAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



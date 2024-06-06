package com.example.bookingapptim14.dtos;

public class Token {
    private long userId;
    private String token;

    public Token() {
    }

    public Token(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

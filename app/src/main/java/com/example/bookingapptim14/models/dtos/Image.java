package com.example.bookingapptim14.models.dtos;

public class Image {

    private String imageBytes;
    private String imageType;

    public Image() { }

    public Image(String imageBytes, String imageType) {
        this.imageBytes = imageBytes;
        this.imageType = imageType;
    }

    public String getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(String imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}

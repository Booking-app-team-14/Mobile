package com.example.bookingapptim14.models.dtos.AccommodationDTO;

public class CheckableAmenity extends AmenityDTO {
    private boolean checked;

    public CheckableAmenity(AmenityDTO amenity) {
        super(amenity.getId(), amenity.getName(), amenity.getDescription(), amenity.getIcon(), amenity.getIconType(), amenity.getIconBytes());
        this.checked = false;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
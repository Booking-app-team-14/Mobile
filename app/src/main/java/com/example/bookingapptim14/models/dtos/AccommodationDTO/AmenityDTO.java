package com.example.bookingapptim14.models.dtos.AccommodationDTO;

public class AmenityDTO {

    private Long id;
    private String name;
    private String description;
    private String icon;
    private String iconType;
    private String iconBytes;

    public AmenityDTO() { }

    public AmenityDTO(Long id, String name, String description, String icon, String iconType, String iconBytes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.iconType = iconType;
        this.iconBytes = iconBytes;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getIconBytes() {
        return iconBytes;
    }

    public void setIconBytes(String iconBytes) {
        this.iconBytes = iconBytes;
    }
}

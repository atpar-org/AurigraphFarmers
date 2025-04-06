package com.example.aurigraph.farmers.DTO;

public class LocationPointDTO {
    private String locationCode;
    private String locationName;

    public LocationPointDTO(String locationCode, String locationName) {
        this.locationCode = locationCode;
        this.locationName = locationName;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
// Getters and setters
}

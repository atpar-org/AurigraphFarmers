package com.example.aurigraph.farmers.DTO;

public class DynamicLocationResponseDTO {
    private Long countryId;
    private String countryName;
    private DynamicLocationNodeDTO location;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public DynamicLocationNodeDTO getLocation() {
        return location;
    }

    public void setLocation(DynamicLocationNodeDTO location) {
        this.location = location;
    }
}

package com.example.aurigraph.farmers.DTO;

import java.util.List;

public class LocationDataDTO {
  private List<LocationPointDTO> locationPointDTOs;

    public List<LocationPointDTO> getLocationPointDTOs() {
        return locationPointDTOs;
    }

    public void setLocationPointDTOs(List<LocationPointDTO> locationPointDTOs) {
        this.locationPointDTOs = locationPointDTOs;
    }
}

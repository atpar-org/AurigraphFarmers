package com.example.aurigraph.farmers.Mapping;

import com.example.aurigraph.farmers.Domain.PropertyDetails;
import com.example.aurigraph.farmers.Service.PropertyDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PropertyMapping {

    private final PropertyDetailsService propertyDetailsService;

    public PropertyMapping(PropertyDetailsService propertyDetailsService) {
        this.propertyDetailsService = propertyDetailsService;
    }

    public List<PropertyDetails> getPropertyDetails(List<PropertyDetails> givenPropertyDetails) {
        // Iterate through each PropertyDetails object in the list

        List<PropertyDetails> propertyDetails = new ArrayList<>();

        for (PropertyDetails property : givenPropertyDetails) {

            PropertyDetails propertyDetail = new PropertyDetails();
            // Check if itemName is not null and update if needed
            if(property.getId() != null){
                propertyDetail = propertyDetailsService.findById(property.getId()).orElse(null);
                if(propertyDetail == null){
                    propertyDetail = new PropertyDetails();
                }

            }
            if (property.getItemName() != null) {
                propertyDetail.setItemName(property.getItemName()); // Set the item name if not null
            }

            // Check if cropDetails is not null and update if needed
            if (property.getCropDetails() != null) {
                propertyDetail.setCropDetails(property.getCropDetails()); // Set crop details if not null
            }

            // Check if totalArea is not null and update if needed
            if (property.getTotalArea() != null) {
                propertyDetail.setTotalArea(property.getTotalArea()); // Set total area if not null
            }

            // Check if surveyNumbers is not null and update if needed
            if (property.getSurveyNumbers() != null) {
                propertyDetail.setSurveyNumbers(property.getSurveyNumbers()); // Set survey numbers if not null
            }

            // Check if location is not null and update if needed
            if (property.getLocation() != null) {
                propertyDetail.setLocation(property.getLocation()); // Set location if not null
            }
            propertyDetails.add(propertyDetail);
            // Add more fields here if necessary
        }

        // Return the modified list
        return propertyDetails;
    }

}

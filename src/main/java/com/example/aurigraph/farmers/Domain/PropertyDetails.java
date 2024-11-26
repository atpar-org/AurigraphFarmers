package com.example.aurigraph.farmers.Domain;

import jakarta.persistence.*;


@Entity
@Table(name = "property_details")
public class PropertyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Property ID

    @Column(name = "item_name")
    private String itemName; // Item name (e.g., crop details)

    @Column(name = "land_details_id", nullable = false)
    private Long landDetailsId; // Many-to-One relationship with LandDetails

    public Long getLandDetailsId() {
        return landDetailsId;
    }

    public void setLandDetailsId(Long landDetailsId) {
        this.landDetailsId = landDetailsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCropDetails() {
        return cropDetails;
    }

    public void setCropDetails(String cropDetails) {
        this.cropDetails = cropDetails;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public String getSurveyNumbers() {
        return surveyNumbers;
    }

    public void setSurveyNumbers(String surveyNumbers) {
        this.surveyNumbers = surveyNumbers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "crop_details")
    private String cropDetails; // Crop details

    @Column(name = "total_area_hectares")
    private Double totalArea; // Total area of the land in hectares

    @Column(name = "survey_numbers")
    private String surveyNumbers; // Survey numbers (comma-separated)

    @Column(name = "location")
    private String location; // Village, Hobli, District, State

}

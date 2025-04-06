package com.example.aurigraph.farmers.DTO;

public class LocationDTO {
    private Long id;
    private Long countryId;
    private Long levelConfigId;
    private Long parentId;
    private String name;
    private String code;
    private String fullPath;
    private Boolean isCapital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getLevelConfigId() {
        return levelConfigId;
    }

    public void setLevelConfigId(Long levelConfigId) {
        this.levelConfigId = levelConfigId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Boolean getCapital() {
        return isCapital;
    }

    public void setCapital(Boolean capital) {
        isCapital = capital;
    }

    public LocationDTO(Long id, Long countryId, Long levelConfigId, Long parentId,
                       String name, String code, String fullPath, Boolean isCapital) {
        this.id = id;
        this.countryId = countryId;
        this.levelConfigId = levelConfigId;
        this.parentId = parentId;
        this.name = name;
        this.code = code;
        this.fullPath = fullPath;
        this.isCapital = isCapital;
    }

    // Getters and setters
}

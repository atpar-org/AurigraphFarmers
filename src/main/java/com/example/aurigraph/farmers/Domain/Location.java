package com.example.aurigraph.farmers.Domain;

import jakarta.persistence.*;



@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level_config_id", nullable = false)
    private CountryLevelConfig levelConfig;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Location parent;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code" ,unique = true)
    private String code;

    @Column(name = "full_path")
    private String fullPath;

    @Column(name = "is_capital")
    private Boolean isCapital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public CountryLevelConfig getLevelConfig() {
        return levelConfig;
    }

    public void setLevelConfig(CountryLevelConfig levelConfig) {
        this.levelConfig = levelConfig;
    }

    public Location getParent() {
        return parent;
    }

    public void setParent(Location parent) {
        this.parent = parent;
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

    // Getters, setters, constructors
}

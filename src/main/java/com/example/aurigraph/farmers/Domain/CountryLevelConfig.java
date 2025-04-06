package com.example.aurigraph.farmers.Domain;

import jakarta.persistence.*;


@Entity
@Table(name = "country_level_config",
        uniqueConstraints = @UniqueConstraint(columnNames = {"country_id", "level_order"}))
public class CountryLevelConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "level_order", nullable = false)
    private Integer levelOrder;

    @Column(name = "level_name", nullable = false)
    private String levelName;

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

    public Integer getLevelOrder() {
        return levelOrder;
    }

    public void setLevelOrder(Integer levelOrder) {
        this.levelOrder = levelOrder;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }


}

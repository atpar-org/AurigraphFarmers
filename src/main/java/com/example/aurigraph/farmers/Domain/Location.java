package com.example.aurigraph.farmers.Domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_config_id", nullable = false)
    private CountryLevelConfig levelConfig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Location parent;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "full_path")
    private String fullPath;

    @Column(name = "latitude", precision = 10, scale = 6)
    private Double latitude;

    @Column(name = "longitude", precision = 10, scale = 6)
    private Double longitude;

    @Column(name = "is_capital")
    private Boolean isCapital;

    // Getters, setters, constructors
}

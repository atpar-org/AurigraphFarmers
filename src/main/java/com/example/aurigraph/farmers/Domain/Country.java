package com.example.aurigraph.farmers.Domain;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "iso_code", nullable = false, unique = true)
    private String isoCode;

    @Column(name = "continent")
    private String continent;

    @Column(name = "currency")
    private String currency;

    @Column(name = "default_language")
    private String defaultLanguage;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CountryLevelConfig> levelConfigs;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations;

    // Getters, setters, constructors
}

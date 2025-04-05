package com.example.aurigraph.farmers.Domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "country_level_config",
        uniqueConstraints = @UniqueConstraint(columnNames = {"country_id", "level_order"}))
public class CountryLevelConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "level_order", nullable = false)
    private Integer levelOrder;

    @Column(name = "level_name", nullable = false)
    private String levelName;

    // Getters, setters, constructors
}

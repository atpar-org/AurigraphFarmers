package com.example.aurigraph.farmers.Domain;


import jakarta.persistence.*;

@Entity
@Table(name = "land_details_land_owners")
public class LandDetailsLandOwners {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the join table


    public Long getLandOwnerId() {
        return landOwnerId;
    }

    public void setLandOwnerId(Long landOwnerId) {
        this.landOwnerId = landOwnerId;
    }

    public Long getLandDetailsId() {
        return landDetailsId;
    }

    public void setLandDetailsId(Long landDetailsId) {
        this.landDetailsId = landDetailsId;
    }

    @Column(name = "land_details_id", nullable = false)
    private Long landDetailsId;

    @Column(name = "land_owner_id", nullable = false)
    private Long landOwnerId;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

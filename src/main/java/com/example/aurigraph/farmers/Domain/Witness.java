package com.example.aurigraph.farmers.Domain;

import com.example.aurigraph.farmers.AbstractAuditingEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "witness")
public class Witness extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public Long getLandDetailsId() {
        return landDetailsId;
    }

    public void setLandDetailsId(Long landDetailsId) {
        this.landDetailsId = landDetailsId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "land_details_id", nullable = false)
    private Long landDetailsId; // Many-to-One relationship with LandDetails

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "date", nullable = false)
    private Date date;
}

package com.example.aurigraph.farmers.Domain;

import com.example.aurigraph.farmers.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "land_owner")
public class LandOwner extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "landowner_name")
    private String landownerName;  // Landowner Name

    @Column(name = "signature")
    private String signature;  // Landowner Signature

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "aadhaar")
    private String aadhaar;

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getAadhaarUploadPath() {
        return aadhaarUploadPath;
    }

    public void setAadhaarUploadPath(String aadhaarUploadPath) {
        this.aadhaarUploadPath = aadhaarUploadPath;
    }

    public String getLandDeedPath() {
        return landDeedPath;
    }

    public void setLandDeedPath(String landDeedPath) {
        this.landDeedPath = landDeedPath;
    }

    @Column(name = "aadhaar_upload_path")
    private String aadhaarUploadPath;

    @Column(name = "land_deed_path")
    private String landDeedPath;

    @Column(name = "address")
    private String address;

    public String getLandownerName() {
        return landownerName;
    }

    public void setLandownerName(String landownerName) {
        this.landownerName = landownerName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;  // Date of landowner record

    @Column(name = "email")
    private String email;  // Email

    @Column(name = "mobile")
    private String mobile;  // Mobile Number

}

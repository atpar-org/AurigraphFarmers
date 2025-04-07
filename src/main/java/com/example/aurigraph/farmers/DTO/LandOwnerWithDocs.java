package com.example.aurigraph.farmers.DTO;

import java.time.LocalDate;
import java.util.List;

public class LandOwnerWithDocs {
    private Long id;

    private LocalDate date;  // Date of landowner record


    private String email;  // Email


    private String mobile;  // Mobile Number


    private String landownerName;  // Landowner Name

    private String signature;  // Landowner Signature

    private String aadhaar;

    private String address;

    private List<LandOwnerDocsDTO> landOwnerDocsDTOS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<LandOwnerDocsDTO> getLandOwnerDocsDTOS() {
        return landOwnerDocsDTOS;
    }

    public void setLandOwnerDocsDTOS(List<LandOwnerDocsDTO> landOwnerDocsDTOS) {
        this.landOwnerDocsDTOS = landOwnerDocsDTOS;
    }
}

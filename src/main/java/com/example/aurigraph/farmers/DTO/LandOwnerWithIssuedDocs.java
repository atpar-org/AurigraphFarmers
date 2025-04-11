package com.example.aurigraph.farmers.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.util.List;

public class LandOwnerWithIssuedDocs {
    private Long id;

    private String landownerName;  // Landowner Name

    private String signature;  // Landowner Signature

    private LocalDate date;  // Date of landowner record

    private String email;  // Email

    private String mobile;  // Mobile Number

    private String aadhaar;

    private String imageUrl;

    private String address;

    private List<IssuedDocumentDTO> issuedDocuments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<IssuedDocumentDTO> getIssuedDocuments() {
        return issuedDocuments;
    }

    public void setIssuedDocuments(List<IssuedDocumentDTO> issuedDocuments) {
        this.issuedDocuments = issuedDocuments;
    }
}

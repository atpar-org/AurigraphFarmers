package com.example.aurigraph.farmers.DTO;

import com.example.aurigraph.farmers.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

public class LandOwnerDTO {


        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        private String landownerName;  // Landowner Name


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

    public MultipartFile getAadhaarFile() {
        return aadhaarFile;
    }

    public void setAadhaarFile(MultipartFile aadhaarFile) {
        this.aadhaarFile = aadhaarFile;
    }

    public MultipartFile getLandDeedFile() {
        return landDeedFile;
    }

    public void setLandDeedFile(MultipartFile landDeedFile) {
        this.landDeedFile = landDeedFile;
    }

    public void setAadhaar(String aadhaar) {
            this.aadhaar = aadhaar;
        }

        private MultipartFile aadhaarFile;

        private MultipartFile landDeedFile;

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


        private LocalDate date;  // Date of landowner record


        private String email;  // Email


        private String mobile;  // Mobile Number

    }



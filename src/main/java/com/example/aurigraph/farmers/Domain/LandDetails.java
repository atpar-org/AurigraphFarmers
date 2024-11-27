package com.example.aurigraph.farmers.Domain;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "land_details")
public class LandDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number")
    private String accountNumber;  // Account number

    @Column(name = "account_holder")
    private String accountHolder;  // Account Holder name

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "ifsc_code")
    private String ifscCode;  // IFSC Code

    @Column(name = "swift_code")
    private String swiftCode;  // SWIFT Code

    @Column(name = "bank")
    private String bank;  // Bank name

    @Column(name = "branch")
    private String branch;  // Branch name

    @Column(name = "aksmvbs_membership_number")
    private String aksmvbsMembershipNumber;  // Membership Number

    @Column(name = "geo_coordinates")
    private String geoCoordinates; // Stores "latitude,longitude"

    public String getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(String geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }



    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAksmvbsMembershipNumber() {
        return aksmvbsMembershipNumber;
    }

    public void setAksmvbsMembershipNumber(String aksmvbsMembershipNumber) {
        this.aksmvbsMembershipNumber = aksmvbsMembershipNumber;
    }



}

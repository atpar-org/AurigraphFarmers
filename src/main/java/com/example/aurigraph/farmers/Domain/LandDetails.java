package com.example.aurigraph.farmers.Domain;


import com.example.aurigraph.farmers.AbstractAuditingEntity;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(
        name = "land_details",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"location_id", "survey_number"})
        }
)
public class LandDetails extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number")
    private String accountNumber;  // Account number

    @Column(name = "account_holder")
    private String accountHolder;  // Account Holder name

    @Column(name = "date_time_created", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreated;

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

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

    @Column(name = "geo_coordinates" ,nullable = true)
    private String geoCoordinates; // Stores "latitude,longitude"

    public String getBankDetailsPath() {
        return BankDetailsPath;
    }

    public void setBankDetailsPath(String bankDetailsPath) {
        BankDetailsPath = bankDetailsPath;
    }

    @Column(name = "bank_details_path")
    private String BankDetailsPath;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to the User table
    private User user;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    @Column(name = "approved")
    private boolean approved = false;

    @Column(name = "approverName")
    private String approverName;


    @Column(name = "crop_details")
    private String cropDetails; // Crop details

    @Column(name = "total_area_hectares")
    private Double totalAreaHectares; // Total area of the land in hectares

    @Column(name = "survey_number")
    private String surveyNumber; // Survey numbers (comma-separated)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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


//    public LocalDateTime getDateCreated() {
//        return LocalDateTime.now();
//    }

//    public void setDateCreated(LocalDate dateCreated) {
//        this.dateCreated = dateCreated;
//    }



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

    public String getCropDetails() {
        return cropDetails;
    }

    public void setCropDetails(String cropDetails) {
        this.cropDetails = cropDetails;
    }

    public Double getTotalAreaHectares() {
        return totalAreaHectares;
    }

    public void setTotalAreaHectares(Double totalAreaHectares) {
        this.totalAreaHectares = totalAreaHectares;
    }

    public String getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(String surveyNumber) {
        this.surveyNumber = surveyNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

package com.example.aurigraph.farmers.Domain;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"landowner_id", "docType"})
)
public class LandOwnerDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String docUrl;

    private String type;
    private String size;
    private String date;
    private String parent;
    private String mimes;
    private String uri;
    private String description;
    private String issuerId;

    private String issuer;
    @Column(nullable = false)
    private String docType;

    private String docName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "landowner_id", nullable = false)
    private LandOwner landOwner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public LandOwner getLandOwner() {
        return landOwner;
    }

    public void setLandOwner(LandOwner landOwner) {
        this.landOwner = landOwner;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getMimes() {
        return mimes;
    }

    public void setMimes(String mimes) {
        this.mimes = mimes;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}

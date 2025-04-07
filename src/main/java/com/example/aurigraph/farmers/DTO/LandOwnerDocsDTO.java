package com.example.aurigraph.farmers.DTO;

import com.example.aurigraph.farmers.Domain.LandOwner;

public class LandOwnerDocsDTO {

    private IssuedDocumentDTO doc;
    private String docPath;

    public IssuedDocumentDTO getDoc() {
        return doc;
    }

    public void setDoc(IssuedDocumentDTO doc) {
        this.doc = doc;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

}

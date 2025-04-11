package com.example.aurigraph.farmers.DTO;

import com.example.aurigraph.farmers.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class LandOwnerDTO {

        private Long id;

        private String mobile;  // Mobile Number


        private List<IssuedDocumentDTO> docsDetails;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public List<IssuedDocumentDTO> getDocsDetails() {
            return docsDetails;
        }

        public void setDocsDetails(List<IssuedDocumentDTO> docsDetails) {
            this.docsDetails = docsDetails;
        }
}



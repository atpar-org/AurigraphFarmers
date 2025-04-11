package com.example.aurigraph.farmers.Mapping;


import com.example.aurigraph.farmers.DTO.*;
import com.example.aurigraph.farmers.Domain.LandDetailsLandOwners;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Domain.LandOwnerDoc;
import com.example.aurigraph.farmers.Service.ApiSetuService;
import com.example.aurigraph.farmers.Service.Impl.FilesManager;
import com.example.aurigraph.farmers.Service.LandDetailsLandOwnersService;
import com.example.aurigraph.farmers.Service.LandOwnerDocsService;
import com.example.aurigraph.farmers.Service.LandOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Component
public class LandOwnerMapping {

    @Autowired
    private FilesManager filesManager;
    @Autowired
    private LandOwnerService landOwnerService;
    @Autowired
    private ApiSetuService apiSetuService;
    @Autowired
    private LandOwnerDocsService landOwnerDocsService;
    @Autowired
    private LandDetailsLandOwnersService landDetailsLandOwnersService;

    public List<LandOwner> DtosToDomains(CompleteLandDetailsInDTO completeLandDetailsInDTO,Long landDetailsId) throws IOException {


        List<LandOwner> landOwners = new ArrayList<>();
        List<LandOwnerDTO> landOwnerDTOs = completeLandDetailsInDTO.getLandOwners();

        for (LandOwnerDTO landOwnerDTO : landOwnerDTOs) {
            LandOwner landOwner =null;
            if(landOwnerDTO.getId()!=null){

                landOwner = landOwnerService.findById(landOwnerDTO.getId()).orElse(null);

            }
            if(landOwner == null){
                landOwner = new LandOwner();
                List<LandOwner> existingLandOwners = landOwnerService.getLandOwnersByMobile(landOwnerDTO.getMobile());
                if(!existingLandOwners.isEmpty()){
                    for(LandOwner existingLandOwner : existingLandOwners){
                        Optional<LandDetailsLandOwners> landDetailsLandOwners = landDetailsLandOwnersService.findByLandDetailsIdAndLandOwnerId(landDetailsId, existingLandOwner.getId());
                        if (landDetailsLandOwners.isPresent()) {
                            landOwner = existingLandOwner;
                        }
                    }
                }

            }
            if(landOwnerDTO.getId() != null){
                landOwner.setId(landOwnerDTO.getId());
            }
            if(landOwnerDTO.getAadhaar() != null){
                landOwner.setAadhaar(landOwnerDTO.getAadhaar());
            }
            if(landOwnerDTO.getAddress()!=null){
                landOwner.setAddress(landOwnerDTO.getAddress());
            }
            if(landOwnerDTO.getLandownerName()!=null){
                landOwner.setLandownerName(landOwnerDTO.getLandownerName());
            }
            if(landOwnerDTO.getMobile()!=null){
                landOwner.setMobile(landOwnerDTO.getMobile());
            }
            if(landOwnerDTO.getSignature()!=null){
                landOwner.setSignature(landOwnerDTO.getSignature());
            }
            if (landOwnerDTO.getEmail() != null) {
                landOwner.setEmail(landOwnerDTO.getEmail());
            }
            if (landOwnerDTO.getDate()!=null){
                landOwner.setDate(landOwnerDTO.getDate());
            }

            landOwner = landOwnerService.save(landOwner);

            List<String> docsPaths = new ArrayList<>();
            if(!landOwnerDTO.getDocsDetails().isEmpty()){
                List<IssuedDocumentDTO> docsDetails = landOwnerDTO.getDocsDetails();
                List<LandOwnerDocsDTO> landOwnerDocsDTOs = new ArrayList<>();
                for(IssuedDocumentDTO docDetails : docsDetails){
                    LandOwnerDocsDTO landOwnerDocsDTO = new LandOwnerDocsDTO();
                    landOwnerDocsDTO.setDoc(docDetails);
                    if ("ADHAR".equalsIgnoreCase(docDetails.getDoctype())) {
                        AadhaarDetailsDTO aadhaarDetailsDTO = apiSetuService.getDigiLockerAadhaarDocsByUri(docDetails.getUri(),docDetails.getDoctype(),landOwner.getMobile());

                        if(aadhaarDetailsDTO!=null){

                            String docFilePath = filesManager.saveFile(aadhaarDetailsDTO.getImage(), "LandDetails", "LandDetails-" + landDetailsId, "LandOwner","LandOwner-"+landOwner.getId(), docDetails.getDoctype()+"-" +"image", aadhaarDetailsDTO.getImage().getContentType());

                            docsPaths.add(docFilePath);

                            landOwnerDocsDTO.setDocPath(docFilePath);

                            landOwner =aadhaarDetailsToLandOwner(landOwner,aadhaarDetailsDTO);
                            landOwnerService.save(landOwner);
                        }
                    }else{
                        MultipartFile doc =apiSetuService.getDigiLockerDocsByUri(docDetails.getUri(),docDetails.getDoctype(),landOwner.getMobile());
                        String docFilePath = filesManager.saveFile(doc, "LandDetails", "LandDetails-" + landDetailsId, "LandOwner","LandOwner-"+landOwner.getId(), docDetails.getDoctype(), doc.getContentType());

                        docsPaths.add(docFilePath);
                        landOwnerDocsDTO.setDocPath(docFilePath);
                    }


                    landOwnerDocsDTOs.add(landOwnerDocsDTO);
                }
                landOwnerDocsService.saveLandDocs(landOwnerDocsDTOs,landOwner);
            }

//            if (landOwnerDTO.getAadhaarFile()!=null){
//                MultipartFile aadhaarFile = landOwnerDTO.getAadhaarFile();
//                String aadhaarUploadPath="";
//                try {
//                    if (aadhaarFile != null) {
//                        aadhaarUploadPath = filesManager.saveFile(aadhaarFile, "LandDetails", "LandDetails-" + landDetailsId, "LandOwner", "Aadhaar-" + landOwner.getAadhaar(), aadhaarFile.getContentType());
//                    }
//                } catch(Exception e){
//                    e.printStackTrace();
//                }
//                if(!aadhaarUploadPath.isEmpty()){
//                    landOwner.setAadhaarUploadPath(aadhaarUploadPath);
//                }
//            }
//
//            if(landOwnerDTO.getLandDeedFile()!=null){
//                MultipartFile landDeedFile = landOwnerDTO.getLandDeedFile();
//                String landDeedUploadPath="";
//                try{
//
//                    if(landDeedFile!=null){
//                        landDeedUploadPath = filesManager.saveFile(landDeedFile,"LandDetails","LandDetails-"+landDetailsId,"LandOwner","LandDeed-"+landOwner.getAadhaar(),landDeedFile.getContentType());
//                    }
//                } catch(Exception e){
//                    e.printStackTrace();
//                }
//
//                if(!landDeedUploadPath.isEmpty()){
//                    landOwner.setLandDeedPath(landDeedUploadPath);
//                }
//
//            }

            landOwners.add(landOwner);

        }
        return landOwners;

    }


    public List<LandOwnerWithDocs> domainToOutDTO(List<LandOwner> savedLandOwners) {
        List<LandOwnerWithDocs> landOwnersWithDocs = new ArrayList<>();
        for (LandOwner landOwner : savedLandOwners) {
            LandOwnerWithDocs landOwnerWithDocs = new LandOwnerWithDocs();
            List<LandOwnerDoc> landOwnerDocs = landOwnerDocsService.findByLandOwnerId(landOwner.getId());
            List<LandOwnerDocsDTO> landOwnerDocsDTOS = new ArrayList<>();
            for (LandOwnerDoc landOwnerDoc : landOwnerDocs) {
                LandOwnerDocsDTO landOwnerDocsDTO = new LandOwnerDocsDTO();
                IssuedDocumentDTO issuedDocumentDTO = new IssuedDocumentDTO();
                issuedDocumentDTO.setDate(landOwnerDoc.getDate());
                issuedDocumentDTO.setUri(landOwnerDoc.getUri());
                issuedDocumentDTO.setDescription(landOwnerDoc.getDescription());
                issuedDocumentDTO.setParent(landOwnerDoc.getParent());
                issuedDocumentDTO.setType(landOwnerDoc.getType());
                issuedDocumentDTO.setSize(landOwnerDoc.getSize());
                issuedDocumentDTO.setDoctype(landOwnerDoc.getDocType());
                issuedDocumentDTO.setIssuerId(landOwnerDoc.getIssuerId());
                issuedDocumentDTO.setIssuer(landOwnerDoc.getIssuer());
                if(landOwnerDoc.getMimes()!=null){
                    issuedDocumentDTO.setMime(Arrays.asList(landOwnerDoc.getMimes().split(",")));
                }
                issuedDocumentDTO.setName(landOwnerDoc.getDocName());
                landOwnerDocsDTO.setDoc(issuedDocumentDTO);
                landOwnerDocsDTO.setDocPath(landOwnerDoc.getDocUrl());
                landOwnerDocsDTOS.add(landOwnerDocsDTO);
            }
            landOwnerWithDocs.setLandOwnerDocsDTOS(landOwnerDocsDTOS);
            landOwnerWithDocs.setId(landOwner.getId());
            landOwnerWithDocs.setAadhaar(landOwner.getAadhaar());
            landOwnerWithDocs.setAddress(landOwner.getAddress());
            landOwnerWithDocs.setDate(landOwner.getDate());
            landOwnerWithDocs.setEmail(landOwner.getEmail());
            landOwnerWithDocs.setMobile(landOwner.getMobile());
            landOwnerWithDocs.setLandownerName(landOwner.getLandownerName());
            landOwnerWithDocs.setSignature(landOwner.getSignature());

            landOwnersWithDocs.add(landOwnerWithDocs);

        }
        return landOwnersWithDocs;
    }

    public LandOwner aadhaarDetailsToLandOwner(LandOwner owner ,AadhaarDetailsDTO dto) {

        if (isNotBlank(dto.getName())) owner.setLandownerName(dto.getName());
        if (isNotBlank(dto.getUid())) owner.setAadhaar(dto.getUid());

        // Construct address from available Aadhaar fields
        StringBuilder addressBuilder = new StringBuilder();
        if (isNotBlank(dto.getCo())) addressBuilder.append(dto.getCo()).append(", ");
        if (isNotBlank(dto.getLoc())) addressBuilder.append(dto.getLoc()).append(", ");
        if (isNotBlank(dto.getPo())) addressBuilder.append(dto.getPo()).append(", ");
        if (isNotBlank(dto.getVtc())) addressBuilder.append(dto.getVtc()).append(", ");
        if (isNotBlank(dto.getDist())) addressBuilder.append(dto.getDist()).append(", ");
        if (isNotBlank(dto.getState())) addressBuilder.append(dto.getState()).append(", ");
        if (isNotBlank(dto.getPc())) addressBuilder.append("PIN: ").append(dto.getPc());

        String fullAddress = addressBuilder.toString().replaceAll(", $", "");
        if (!fullAddress.isEmpty()) {
            owner.setAddress(fullAddress);
        }

        // Optional: set today's date as record creation date
        owner.setDate(LocalDate.now());

        return owner;
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

}

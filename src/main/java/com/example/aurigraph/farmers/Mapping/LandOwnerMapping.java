package com.example.aurigraph.farmers.Mapping;


import com.example.aurigraph.farmers.DTO.*;
import com.example.aurigraph.farmers.Domain.LandDetailsLandOwners;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Domain.LandOwnerDoc;
import com.example.aurigraph.farmers.Repository.LandOwnerRepository;
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
    private LandOwnerRepository landOwnerRepository;
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
            LandOwner landOwner = DtoToDomain(landOwnerDTO);
            landOwners.add(landOwner);

        }
        return landOwners;

    }


    public LandOwner DtoToDomain(LandOwnerDTO landOwnerDTO) throws IOException {

        LandOwner landOwner =null;
        if(landOwnerDTO.getId()!=null){

            landOwner = landOwnerRepository.findById(landOwnerDTO.getId()).orElse(null);

        }
        if(landOwner == null){
            landOwner = new LandOwner();
            Optional<LandOwner> existingLandOwnerOpt = landOwnerRepository.findByMobile(landOwnerDTO.getMobile());

            if(existingLandOwnerOpt.isPresent()){
                landOwner = existingLandOwnerOpt.get();
            }

        }
//            if(landOwnerDTO.getId() != null){
//                landOwner.setId(landOwnerDTO.getId());
//            }
//            if(landOwnerDTO.getAadhaar() != null){
//                landOwner.setAadhaar(landOwnerDTO.getAadhaar());
//            }
//            if(landOwnerDTO.getAddress()!=null){
//                landOwner.setAddress(landOwnerDTO.getAddress());
//            }
//            if(landOwnerDTO.getLandownerName()!=null){
//                landOwner.setLandownerName(landOwnerDTO.getLandownerName());
//            }
//            if(landOwnerDTO.getMobile()!=null){
//                landOwner.setMobile(landOwnerDTO.getMobile());
//            }
//            if(landOwnerDTO.getSignature()!=null){
//                landOwner.setSignature(landOwnerDTO.getSignature());
//            }
//            if (landOwnerDTO.getEmail() != null) {
//                landOwner.setEmail(landOwnerDTO.getEmail());
//            }
//            if (landOwnerDTO.getDate()!=null){
//                landOwner.setDate(landOwnerDTO.getDate());
//            }
//

        if(landOwnerDTO.getMobile()!=null && landOwner.getMobile()==null){
            landOwner.setMobile(landOwnerDTO.getMobile());
            landOwner = landOwnerRepository.save(landOwner);
        }



        if(!landOwnerDTO.getDocsDetails().isEmpty()){
            List<IssuedDocumentDTO> docsDetails = landOwnerDTO.getDocsDetails();
            List<LandOwnerDocsDTO> landOwnerDocsDTOs = new ArrayList<>();
            for(IssuedDocumentDTO docDetails : docsDetails){
                LandOwnerDocsDTO landOwnerDocsDTO = new LandOwnerDocsDTO();
                landOwnerDocsDTO.setDoc(docDetails);
                if ("ADHAR".equalsIgnoreCase(docDetails.getDoctype())) {
                    AadhaarDetailsDTO aadhaarDetailsDTO = apiSetuService.getDigiLockerAadhaarDocsByUri(docDetails.getUri(),docDetails.getDoctype(),landOwner.getMobile());

                    if(aadhaarDetailsDTO!=null){
                        landOwner = saveLandOwnerByAadhaarDetails(aadhaarDetailsDTO,docDetails.getDoctype(),landOwner);
                        landOwnerDocsDTO.setDocPath(landOwner.getImageUrl());
                    }
                }else{
                    MultipartFile doc =apiSetuService.getDigiLockerDocsByUri(docDetails.getUri(),docDetails.getDoctype(),landOwner.getMobile());
                    String docFilePath = filesManager.saveFile(doc, "LandOwner","LandOwner-"+landOwner.getId(), docDetails.getDoctype(), doc.getContentType());


                    landOwnerDocsDTO.setDocPath(docFilePath);
                }


                landOwnerDocsDTOs.add(landOwnerDocsDTO);
            }
            landOwnerDocsService.saveLandDocs(landOwnerDocsDTOs,landOwner);
        }

        return landOwner;
    }

    public LandOwner saveLandOwnerByAadhaarDetails(AadhaarDetailsDTO aadhaarDetailsDTO,String docType, LandOwner landOwner) throws IOException {
        String docFilePath = filesManager.saveFile(aadhaarDetailsDTO.getImage(), "LandOwner","LandOwner-"+landOwner.getId(), docType+"-" +"image", aadhaarDetailsDTO.getImage().getContentType());

        landOwner =aadhaarDetailsToLandOwner(landOwner,aadhaarDetailsDTO,docFilePath);
        return  landOwnerRepository.save(landOwner);

    }


    public List<LandOwnerWithDocs> domainsToOutDTOs(List<LandOwner> savedLandOwners) {
        List<LandOwnerWithDocs> landOwnersWithDocs = new ArrayList<>();
        for (LandOwner landOwner : savedLandOwners) {
            LandOwnerWithDocs landOwnerWithDocs =domainToOutDTO(landOwner);
            landOwnersWithDocs.add(landOwnerWithDocs);

        }
        return landOwnersWithDocs;
    }

    public LandOwnerWithIssuedDocs domainToDTO(LandOwner landOwner) {
        LandOwnerWithIssuedDocs landOwnerWithIssuedDocs = new LandOwnerWithIssuedDocs();

        if (landOwner.getId() != null) {
            landOwnerWithIssuedDocs.setId(landOwner.getId());
        }
        if (landOwner.getMobile() != null) {
            landOwnerWithIssuedDocs.setMobile(landOwner.getMobile());
        }
        if (landOwner.getLandownerName() != null) {
            landOwnerWithIssuedDocs.setLandownerName(landOwner.getLandownerName());
        }
        if (landOwner.getImageUrl() != null) {
            landOwnerWithIssuedDocs.setImageUrl(landOwner.getImageUrl());
        }
        if (landOwner.getAadhaar() != null) {
            landOwnerWithIssuedDocs.setAadhaar(landOwner.getAadhaar());
        }
        if (landOwner.getAddress() != null) {
            landOwnerWithIssuedDocs.setAddress(landOwner.getAddress());
        }
        if (landOwner.getEmail() != null) {
            landOwnerWithIssuedDocs.setEmail(landOwner.getEmail());
        }
        if (landOwner.getDate() != null) {
            landOwnerWithIssuedDocs.setDate(landOwner.getDate());
        }
        if (landOwner.getSignature() != null) {
            landOwnerWithIssuedDocs.setSignature(landOwner.getSignature());
        }

        return landOwnerWithIssuedDocs;
    }

    public LandOwner aadhaarDetailsToLandOwner(LandOwner owner ,AadhaarDetailsDTO dto,String docFilePath) throws IOException {

        if (isNotBlank(dto.getName())) owner.setLandownerName(dto.getName());
        if (isNotBlank(dto.getUid())) owner.setAadhaar(dto.getUid());
        if (docFilePath != null) {owner.setImageUrl(docFilePath);}
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

    public LandOwnerWithDocs domainToOutDTO(LandOwner landOwner) {
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

       return landOwnerWithDocs;
    }
}

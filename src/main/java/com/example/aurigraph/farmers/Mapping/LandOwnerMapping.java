package com.example.aurigraph.farmers.Mapping;


import com.example.aurigraph.farmers.DTO.CompleteLandDetailsInDTO;
import com.example.aurigraph.farmers.DTO.LandOwnerDTO;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Service.Impl.FilesManager;
import com.example.aurigraph.farmers.Service.LandOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class LandOwnerMapping {

    @Autowired
    private FilesManager filesManager;
    @Autowired
    private LandOwnerService landOwnerService;

    public List<LandOwner> DtosToDomains(CompleteLandDetailsInDTO completeLandDetailsInDTO,Long landDetailsId) {


        List<LandOwner> landOwners = new ArrayList<>();
        List<LandOwnerDTO> landOwnerDTOs = completeLandDetailsInDTO.getLandOwners();

        for (LandOwnerDTO landOwnerDTO : landOwnerDTOs) {
            LandOwner landOwner = new LandOwner();
            if(landOwnerDTO.getId()!=null){

                landOwner = landOwnerService.findById(landOwnerDTO.getId()).orElse(null);
                if(landOwner == null){
                    landOwner = new LandOwner();
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


            if (landOwnerDTO.getAadhaarFile()!=null){
                MultipartFile aadhaarFile = landOwnerDTO.getAadhaarFile();
                String aadhaarUploadPath="";
                try {
                    if (aadhaarFile != null) {
                        aadhaarUploadPath = filesManager.saveFile(aadhaarFile, "LandDetails", "LandDetails-" + landDetailsId, "LandOwner", "Aadhaar-" + landOwner.getAadhaar(), aadhaarFile.getContentType());
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
                if(!aadhaarUploadPath.isEmpty()){
                    landOwner.setAadhaarUploadPath(aadhaarUploadPath);
                }
            }

            if(landOwnerDTO.getLandDeedFile()!=null){
                MultipartFile landDeedFile = landOwnerDTO.getLandDeedFile();
                String landDeedUploadPath="";
                try{

                    if(landDeedFile!=null){
                        landDeedUploadPath = filesManager.saveFile(landDeedFile,"LandDetails","LandDetails-"+landDetailsId,"LandOwner","LandDeed-"+landOwner.getAadhaar(),landDeedFile.getContentType());
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }

                if(!landDeedUploadPath.isEmpty()){
                    landOwner.setLandDeedPath(landDeedUploadPath);
                }

            }

            landOwners.add(landOwner);

        }
        return landOwners;

    }
}

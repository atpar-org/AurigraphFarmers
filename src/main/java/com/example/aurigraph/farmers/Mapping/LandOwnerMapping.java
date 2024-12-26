package com.example.aurigraph.farmers.Mapping;


import com.example.aurigraph.farmers.DTO.CompleteLandDetailsInDTO;
import com.example.aurigraph.farmers.DTO.LandOwnerDTO;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Service.Impl.FilesManager;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class LandOwnerMapping {
    public List<LandOwner> DtosToDomains(CompleteLandDetailsInDTO completeLandDetailsInDTO,Long landDetailsId) {
        List<LandOwner> landOwners = new ArrayList<>();
        List<LandOwnerDTO> landOwnerDTOs = completeLandDetailsInDTO.getLandOwners();
        for (LandOwnerDTO landOwnerDTO : landOwnerDTOs) {
            LandOwner landOwner = new LandOwner();
            landOwner.setId(landOwnerDTO.getId());
            landOwner.setAadhaar(landOwnerDTO.getAadhaar());
            landOwner.setAddress(landOwnerDTO.getAddress());
            landOwner.setLandownerName(landOwnerDTO.getLandownerName());
            landOwner.setMobile(landOwnerDTO.getMobile());
            landOwner.setSignature(landOwnerDTO.getSignature());
            landOwner.setEmail(landOwnerDTO.getEmail());
            landOwner.setDate(landOwnerDTO.getDate());


            MultipartFile aadhaarFile = landOwnerDTO.getAadhaarFile();
            MultipartFile landDeedFile = landOwnerDTO.getLandDeedFile();

            String aadhaarUploadPath="";
            String landDeedUploadPath="";
            try{
                if(aadhaarFile!=null){
                    aadhaarUploadPath = FilesManager.saveFile(aadhaarFile,"LandDetails","LandDetails-"+landDetailsId,"LandOwner","Aadhaar-"+landOwner.getAadhaar(),aadhaarFile.getContentType());
                }
                if(landDeedFile!=null){
                    landDeedUploadPath = FilesManager.saveFile(aadhaarFile,"LandDetails","LandDetails-"+landDetailsId,"LandOwner","LandDeed-"+landOwner.getAadhaar(),landDeedFile.getContentType());
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            if(!aadhaarUploadPath.isEmpty()){
                landOwner.setAadhaarUploadPath(aadhaarUploadPath);
            }
            if(!landDeedUploadPath.isEmpty()){
                landOwner.setLandDeedPath(landDeedUploadPath);
            }

            landOwners.add(landOwner);

        }
        return landOwners;

    }
}

package com.example.aurigraph.farmers.Mapping;

import com.example.aurigraph.farmers.DTO.CompleteLandDetailsInDTO;
import com.example.aurigraph.farmers.DTO.CompleteLandDetailsOutDTO;
import com.example.aurigraph.farmers.Domain.LandDetails;
import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Repository.LandDetailsRepository;
import com.example.aurigraph.farmers.Repository.UserRepository;
import com.example.aurigraph.farmers.Security.SecurityUtils;
import com.example.aurigraph.farmers.Service.LandDetailsService;
import org.springframework.stereotype.Component;



@Component
public class LandDetailsMapping {


    private final UserRepository userRepository;
    private final LandDetailsRepository landDetailsRepository;

    public LandDetailsMapping(UserRepository userRepository, LandDetailsRepository landDetailsRepository) {
        this.userRepository = userRepository;
        this.landDetailsRepository = landDetailsRepository;

    }

    public CompleteLandDetailsOutDTO domainToDTO(LandDetails landDetails) {
        if (landDetails == null) {
            return null; // Handle null case to prevent NullPointerException
        }

        CompleteLandDetailsOutDTO completeLandDetailsOutDTO = new CompleteLandDetailsOutDTO();

        // Map simple fields
        completeLandDetailsOutDTO.setId(landDetails.getId());
        completeLandDetailsOutDTO.setAccountNumber(landDetails.getAccountNumber());
        completeLandDetailsOutDTO.setAccountHolder(landDetails.getAccountHolder());
        completeLandDetailsOutDTO.setDateCreated(landDetails.getDateCreated());
        completeLandDetailsOutDTO.setIfscCode(landDetails.getIfscCode());
        completeLandDetailsOutDTO.setSwiftCode(landDetails.getSwiftCode());
        completeLandDetailsOutDTO.setBank(landDetails.getBank());
        completeLandDetailsOutDTO.setBranch(landDetails.getBranch());
        completeLandDetailsOutDTO.setAksmvbsMembershipNumber(landDetails.getAksmvbsMembershipNumber());
        completeLandDetailsOutDTO.setGeoCoordinates(landDetails.getGeoCoordinates());
        completeLandDetailsOutDTO.setBankDetailsPath(landDetails.getBankDetailsPath());
        completeLandDetailsOutDTO.setUserId(landDetails.getUser().getId());
        completeLandDetailsOutDTO.setCreatedBy(landDetails.getCreatedBy());
        completeLandDetailsOutDTO.setCreatedDate(landDetails.getCreatedDate());
        completeLandDetailsOutDTO.setLastModifiedBy(landDetails.getLastModifiedBy());
        completeLandDetailsOutDTO.setLastModifiedDate(landDetails.getLastModifiedDate());
        completeLandDetailsOutDTO.setApproved(landDetails.isApproved());
        completeLandDetailsOutDTO.setApproverName(landDetails.getApproverName());
        // Add additional mappings if needed

        return completeLandDetailsOutDTO;
    }

    public LandDetails dtoToDomain(CompleteLandDetailsInDTO completeLandDetailsInDTO) {
        if (completeLandDetailsInDTO == null) {
            return null; // Handle null case to prevent NullPointerException
        }

        LandDetails landDetails = new LandDetails();



        String currentUser = SecurityUtils.getCurrentUserLogin();

        User user  = userRepository.findByPhoneNumber(currentUser).orElse(null);
        // Map simple fields
        if(completeLandDetailsInDTO.getId() != null) {
            landDetails = landDetailsRepository.findById(completeLandDetailsInDTO.getId()).orElse(null);
            if(landDetails == null) {
                landDetails = new LandDetails();
            }
        }

        if(completeLandDetailsInDTO.getAccountNumber()!=null) {
            landDetails.setAccountNumber(completeLandDetailsInDTO.getAccountNumber());
        }
        if(completeLandDetailsInDTO.getAccountHolder()!=null) {
            landDetails.setAccountHolder(completeLandDetailsInDTO.getAccountHolder());
        }

//        commented to get the date and time instant
//        landDetails.setDateCreated(completeLandDetailsInDTO.getDateCreated());
       if(completeLandDetailsInDTO.getIfscCode()!=null) {
           landDetails.setIfscCode(completeLandDetailsInDTO.getIfscCode());
       }
      if(completeLandDetailsInDTO.getSwiftCode()!=null) {
          landDetails.setSwiftCode(completeLandDetailsInDTO.getSwiftCode());
      }
      if(completeLandDetailsInDTO.getBank()!=null) {
          landDetails.setBank(completeLandDetailsInDTO.getBank());
        }
      if(completeLandDetailsInDTO.getBranch()!=null) {
          landDetails.setBranch(completeLandDetailsInDTO.getBranch());
      }
      if(completeLandDetailsInDTO.getAksmvbsMembershipNumber()!=null) {
          landDetails.setAksmvbsMembershipNumber(completeLandDetailsInDTO.getAksmvbsMembershipNumber());
      }
      if(completeLandDetailsInDTO.getGeoCoordinates()!=null) {
          landDetails.setGeoCoordinates(completeLandDetailsInDTO.getGeoCoordinates());
      }
       if(completeLandDetailsInDTO.isApproved()!=landDetails.isApproved()) {
           landDetails.setApproved(completeLandDetailsInDTO.isApproved());
       }
        if(completeLandDetailsInDTO.getApproverName()!=null) {
            landDetails.setApproverName(completeLandDetailsInDTO.getApproverName());
        }

        landDetails.setUser(user);

        // Add additional mappings if needed
        // Example: If there are other fields that need to be mapped from DTO to entity

        return landDetails;
    }
}

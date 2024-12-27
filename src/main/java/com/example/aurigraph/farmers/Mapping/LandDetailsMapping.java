package com.example.aurigraph.farmers.Mapping;

import com.example.aurigraph.farmers.DTO.CompleteLandDetailsInDTO;
import com.example.aurigraph.farmers.DTO.CompleteLandDetailsOutDTO;
import com.example.aurigraph.farmers.Domain.LandDetails;
import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Repository.UserRepository;
import com.example.aurigraph.farmers.Security.SecurityUtils;
import org.springframework.stereotype.Component;



@Component
public class LandDetailsMapping {


    private final UserRepository userRepository;

    public LandDetailsMapping(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        User user  = userRepository.findByEmail(currentUser).orElse(null);
        // Map simple fields
        landDetails.setId(completeLandDetailsInDTO.getId());
        landDetails.setAccountNumber(completeLandDetailsInDTO.getAccountNumber());
        landDetails.setAccountHolder(completeLandDetailsInDTO.getAccountHolder());
        landDetails.setDateCreated(completeLandDetailsInDTO.getDateCreated());
        landDetails.setIfscCode(completeLandDetailsInDTO.getIfscCode());
        landDetails.setSwiftCode(completeLandDetailsInDTO.getSwiftCode());
        landDetails.setBank(completeLandDetailsInDTO.getBank());
        landDetails.setBranch(completeLandDetailsInDTO.getBranch());
        landDetails.setAksmvbsMembershipNumber(completeLandDetailsInDTO.getAksmvbsMembershipNumber());
        landDetails.setGeoCoordinates(completeLandDetailsInDTO.getGeoCoordinates());
        landDetails.setApproved(completeLandDetailsInDTO.isApproved());
        landDetails.setApproverName(completeLandDetailsInDTO.getApproverName());
        landDetails.setUser(user);

        // Add additional mappings if needed
        // Example: If there are other fields that need to be mapped from DTO to entity

        return landDetails;
    }
}

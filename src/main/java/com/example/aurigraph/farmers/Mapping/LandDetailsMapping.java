package com.example.aurigraph.farmers.Mapping;

import com.example.aurigraph.farmers.DTO.CompleteLandDetailsDTO;
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

    public CompleteLandDetailsDTO domainToDTO(LandDetails landDetails) {
        if (landDetails == null) {
            return null; // Handle null case to prevent NullPointerException
        }

        CompleteLandDetailsDTO completeLandDetailsDTO = new CompleteLandDetailsDTO();

        // Map simple fields
        completeLandDetailsDTO.setId(landDetails.getId());
        completeLandDetailsDTO.setAccountNumber(landDetails.getAccountNumber());
        completeLandDetailsDTO.setAccountHolder(landDetails.getAccountHolder());
        completeLandDetailsDTO.setDateCreated(landDetails.getDateCreated());
        completeLandDetailsDTO.setIfscCode(landDetails.getIfscCode());
        completeLandDetailsDTO.setSwiftCode(landDetails.getSwiftCode());
        completeLandDetailsDTO.setBank(landDetails.getBank());
        completeLandDetailsDTO.setBranch(landDetails.getBranch());
        completeLandDetailsDTO.setAksmvbsMembershipNumber(landDetails.getAksmvbsMembershipNumber());
        completeLandDetailsDTO.setGeoCoordinates(landDetails.getGeoCoordinates());
        completeLandDetailsDTO.setUserId(landDetails.getUser().getId());
        completeLandDetailsDTO.setCreatedBy(landDetails.getCreatedBy());
        completeLandDetailsDTO.setCreatedDate(landDetails.getCreatedDate());
        completeLandDetailsDTO.setLastModifiedBy(landDetails.getLastModifiedBy());
        completeLandDetailsDTO.setLastModifiedDate(landDetails.getLastModifiedDate());
        // Add additional mappings if needed

        return completeLandDetailsDTO;
    }

    public LandDetails dtoToDomain(CompleteLandDetailsDTO completeLandDetailsDTO) {
        if (completeLandDetailsDTO == null) {
            return null; // Handle null case to prevent NullPointerException
        }

        LandDetails landDetails = new LandDetails();

        String currentUser = SecurityUtils.getCurrentUserLogin();

        User user  = userRepository.findByEmail(currentUser).orElse(null);
        // Map simple fields
        landDetails.setId(completeLandDetailsDTO.getId());
        landDetails.setAccountNumber(completeLandDetailsDTO.getAccountNumber());
        landDetails.setAccountHolder(completeLandDetailsDTO.getAccountHolder());
        landDetails.setDateCreated(completeLandDetailsDTO.getDateCreated());
        landDetails.setIfscCode(completeLandDetailsDTO.getIfscCode());
        landDetails.setSwiftCode(completeLandDetailsDTO.getSwiftCode());
        landDetails.setBank(completeLandDetailsDTO.getBank());
        landDetails.setBranch(completeLandDetailsDTO.getBranch());
        landDetails.setAksmvbsMembershipNumber(completeLandDetailsDTO.getAksmvbsMembershipNumber());
        landDetails.setGeoCoordinates(completeLandDetailsDTO.getGeoCoordinates());
        landDetails.setUser(user);

        // Add additional mappings if needed
        // Example: If there are other fields that need to be mapped from DTO to entity

        return landDetails;
    }
}

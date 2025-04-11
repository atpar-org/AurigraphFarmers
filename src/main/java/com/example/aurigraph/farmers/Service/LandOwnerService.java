package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.DTO.LandOwnerDTO;
import com.example.aurigraph.farmers.DTO.LandOwnerWithDocs;
import com.example.aurigraph.farmers.Domain.LandOwner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface LandOwnerService {
    List<LandOwner> findAll();

    Optional<LandOwner> findById(Long id);

    LandOwner save(LandOwner landOwner);

    Optional<LandOwner> update(Long id, LandOwner updatedLandOwner);

    boolean delete(Long id);

    boolean deleteLandOwner(Long id);

    Optional<LandOwner> getLandOwnerByMobile(String mobile);

    LandOwnerWithDocs saveLandOwner(LandOwnerDTO landOwnerDTO) throws IOException;
}

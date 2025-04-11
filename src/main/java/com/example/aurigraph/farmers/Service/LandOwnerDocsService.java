package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.DTO.LandOwnerDocsDTO;

import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Domain.LandOwnerDoc;

import java.util.List;

public interface LandOwnerDocsService {
    LandOwnerDoc save(LandOwnerDocsDTO landOwnerDocsDTO);

    void saveLandDocs(List<LandOwnerDocsDTO> landOwnerDocsDTOS, LandOwner landOwner);

    LandOwnerDoc findById(Long id);

    List<LandOwnerDoc> findAll();

    List<LandOwnerDoc> findByLandOwnerId(Long landOwnerId);

    void deleteByLandOwnerId(Long landOwnerId);
}

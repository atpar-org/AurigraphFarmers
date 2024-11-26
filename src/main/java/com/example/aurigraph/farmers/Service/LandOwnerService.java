package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.Domain.LandOwner;

import java.util.List;
import java.util.Optional;

public interface LandOwnerService {
    List<LandOwner> findAll();

    Optional<LandOwner> findById(Long id);

    LandOwner save(LandOwner landOwner);

    Optional<LandOwner> update(Long id, LandOwner updatedLandOwner);

    boolean delete(Long id);
}

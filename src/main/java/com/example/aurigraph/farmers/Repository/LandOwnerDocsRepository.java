package com.example.aurigraph.farmers.Repository;

import com.example.aurigraph.farmers.Domain.LandOwnerDoc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LandOwnerDocsRepository extends CrudRepository<LandOwnerDoc, Long> {

    Optional<LandOwnerDoc> findByDocTypeAndLandOwnerId(String docType, Long landOwnerId);

    List<LandOwnerDoc> findByLandOwnerId(Long landOwnerId);

    List<LandOwnerDoc> findAll ();
}

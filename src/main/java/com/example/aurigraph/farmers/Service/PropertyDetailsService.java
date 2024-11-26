package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.Domain.PropertyDetails;

import java.util.List;
import java.util.Optional;

public interface PropertyDetailsService {
    List<PropertyDetails> findAll();

    Optional<PropertyDetails> findById(Long id);

    PropertyDetails save(PropertyDetails propertyDetails);

    Optional<PropertyDetails> update(Long id, PropertyDetails updatedPropertyDetails);

    boolean delete(Long id);

    List<PropertyDetails> findByLandDetailsId(Long landDetailsId);
}

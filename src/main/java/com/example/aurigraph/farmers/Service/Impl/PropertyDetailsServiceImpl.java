package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.Domain.PropertyDetails;
import com.example.aurigraph.farmers.Repository.PropertyDetailsRepository;
import com.example.aurigraph.farmers.Service.PropertyDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyDetailsServiceImpl implements PropertyDetailsService {

    private final PropertyDetailsRepository propertyDetailsRepository;

    public PropertyDetailsServiceImpl(PropertyDetailsRepository propertyDetailsRepository) {
        this.propertyDetailsRepository = propertyDetailsRepository;
    }
    @Override
    public List<PropertyDetails> findAll() {
        return (List<PropertyDetails>) propertyDetailsRepository.findAll();
    }
    @Override
    public Optional<PropertyDetails> findById(Long id) {
        return propertyDetailsRepository.findById(id);
    }
    @Override
    public PropertyDetails save(PropertyDetails propertyDetails) {
        return propertyDetailsRepository.save(propertyDetails);
    }
    @Override
    public Optional<PropertyDetails> update(Long id, PropertyDetails updatedPropertyDetails) {
        return propertyDetailsRepository.findById(id)
                .map(existing -> {
                    existing.setItemName(updatedPropertyDetails.getItemName());
                    existing.setCropDetails(updatedPropertyDetails.getCropDetails());
                    existing.setTotalArea(updatedPropertyDetails.getTotalArea());
                    existing.setSurveyNumbers(updatedPropertyDetails.getSurveyNumbers());
                    existing.setLocation(updatedPropertyDetails.getLocation());
                    return propertyDetailsRepository.save(existing);
                });
    }
    @Override
    public boolean delete(Long id) {
        if (propertyDetailsRepository.existsById(id)) {
            propertyDetailsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<PropertyDetails> findByLandDetailsId(Long findByLandDetailsId) {
         return  propertyDetailsRepository.findByLandDetailsId(findByLandDetailsId);
    }


}

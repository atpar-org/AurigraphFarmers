package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Repository.LandOwnerRepository;
import com.example.aurigraph.farmers.Service.LandOwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LandOwnerServiceImpl implements LandOwnerService {

    private final LandOwnerRepository landOwnerRepository;

    public LandOwnerServiceImpl(LandOwnerRepository landOwnerRepository) {
        this.landOwnerRepository = landOwnerRepository;
    }
    @Override
    public List<LandOwner> findAll() {
        return (List<LandOwner>) landOwnerRepository.findAll();
    }
    @Override
    public Optional<LandOwner> findById(Long id) {
        return landOwnerRepository.findById(id);
    }
    @Override
    public LandOwner save(LandOwner landOwner) {
        return landOwnerRepository.save(landOwner);
    }
    @Override
    public Optional<LandOwner> update(Long id, LandOwner updatedLandOwner) {
        return landOwnerRepository.findById(id)
                .map(existing -> {
                    existing.setLandownerName(updatedLandOwner.getLandownerName());
                    existing.setSignature(updatedLandOwner.getSignature());
                    existing.setAadhar(updatedLandOwner.getAadhar());
                    existing.setDate(updatedLandOwner.getDate());
                    existing.setEmail(updatedLandOwner.getEmail());
                    existing.setMobile(updatedLandOwner.getMobile());
                    existing.setAddress(updatedLandOwner.getAddress());
                    return landOwnerRepository.save(existing);
                });
    }
    @Override
    public boolean delete(Long id) {
        if (landOwnerRepository.existsById(id)) {
            landOwnerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.Domain.LandDetailsLandOwners;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Repository.LandDetailsLandOwnersRepository;
import com.example.aurigraph.farmers.Repository.LandOwnerRepository;
import com.example.aurigraph.farmers.Service.LandOwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LandOwnerServiceImpl implements LandOwnerService {

    private final LandOwnerRepository landOwnerRepository;
    private final LandDetailsLandOwnersRepository landDetailsLandOwnersRepository;

    private static final Logger logger = LoggerFactory.getLogger(LandOwnerServiceImpl.class);
    public LandOwnerServiceImpl(LandOwnerRepository landOwnerRepository, LandDetailsLandOwnersRepository landDetailsLandOwnersRepository) {
        this.landOwnerRepository = landOwnerRepository;
        this.landDetailsLandOwnersRepository = landDetailsLandOwnersRepository;
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
                    existing.setAadhaar(updatedLandOwner.getAadhaar());
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

    @Override
    public boolean deleteLandOwner(Long id) {

        List<LandDetailsLandOwners> landDetailsLandOwners = landDetailsLandOwnersRepository.findByLandOwnerId(id);
        for(LandDetailsLandOwners landDetailsLandOwner : landDetailsLandOwners) {
            try{
                landDetailsLandOwnersRepository.deleteById(landDetailsLandOwner.getId());
            }catch (Exception e){
                logger.info("Failed to deleted landDetailsLandOwner: " + landDetailsLandOwner.getId());
                return false;
            }
            try{
                landOwnerRepository.deleteById(landDetailsLandOwner.getLandOwnerId());
            }catch (Exception e){
                logger.info("Failed to deleted landOwner: " + id);
                return false;
            }

        }
        return true;
    }
}

package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.DTO.LandOwnerDTO;
import com.example.aurigraph.farmers.DTO.LandOwnerWithDocs;
import com.example.aurigraph.farmers.Domain.LandDetailsLandOwners;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Mapping.LandOwnerMapping;
import com.example.aurigraph.farmers.Repository.LandDetailsLandOwnersRepository;
import com.example.aurigraph.farmers.Repository.LandOwnerRepository;
import com.example.aurigraph.farmers.Service.LandOwnerDocsService;
import com.example.aurigraph.farmers.Service.LandOwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public class LandOwnerServiceImpl implements LandOwnerService {

    private final LandOwnerRepository landOwnerRepository;
    private final LandDetailsLandOwnersRepository landDetailsLandOwnersRepository;
    private final LandOwnerDocsService landOwnerDocsService;

    private static final Logger logger = LoggerFactory.getLogger(LandOwnerServiceImpl.class);
    private final LandOwnerMapping landOwnerMapping;

    public LandOwnerServiceImpl(LandOwnerRepository landOwnerRepository, LandDetailsLandOwnersRepository landDetailsLandOwnersRepository, LandOwnerDocsService landOwnerDocsService, LandOwnerMapping landOwnerMapping) {
        this.landOwnerRepository = landOwnerRepository;
        this.landDetailsLandOwnersRepository = landDetailsLandOwnersRepository;
        this.landOwnerDocsService = landOwnerDocsService;
        this.landOwnerMapping = landOwnerMapping;
    }
    @Override
    public List<LandOwner> findAll() {
        return landOwnerRepository.findAll();
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
                landOwnerDocsService.deleteByLandOwnerId(landDetailsLandOwner.getLandOwnerId());
            }catch (Exception e){
                logger.info("Failed to deleted LandOwnerDocs for LandOwner : " + landDetailsLandOwner.getLandOwnerId());
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

    @Override
    public Optional<LandOwner> getLandOwnerByMobile(String mobile) {
        return landOwnerRepository.findByMobile(mobile);
    }

    @Override
    public LandOwnerWithDocs saveLandOwner(LandOwnerDTO landOwnerDTO) throws IOException {
       LandOwner landOwner = landOwnerMapping.DtoToDomain(landOwnerDTO);
        return landOwnerMapping.domainToOutDTO(landOwner);

    }
}

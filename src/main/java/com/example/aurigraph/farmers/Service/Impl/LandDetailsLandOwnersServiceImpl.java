package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.Domain.LandDetailsLandOwners;
import com.example.aurigraph.farmers.Repository.LandDetailsLandOwnersRepository;
import com.example.aurigraph.farmers.Service.LandDetailsLandOwnersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LandDetailsLandOwnersServiceImpl implements LandDetailsLandOwnersService {

    @Autowired
    private LandDetailsLandOwnersRepository repository;

    @Override
    public LandDetailsLandOwners save(LandDetailsLandOwners entity) {
        return repository.save(entity);
    }

    @Override
    public LandDetailsLandOwners findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public LandDetailsLandOwners update(Long id, LandDetailsLandOwners entity) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }
        entity.setId(id); // Ensure the entity has the correct ID
        return repository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }
        try {
            repository.deleteById(id);
            return true;
        }
        catch (EmptyResultDataAccessException e) {
            return false;
        }

    }

    @Override
    public Optional<LandDetailsLandOwners> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<LandDetailsLandOwners> getAll() {
        return (List<LandDetailsLandOwners>) repository.findAll();
    }

    @Override
    public List<LandDetailsLandOwners> findByLandDetailsId(Long landDetailsId) {
        return repository.findByLandDetailsId(landDetailsId);
    }

    @Override
    public  Optional<LandDetailsLandOwners> findByLandDetailsIdAndLandOwnerId(Long landDetailsId, Long landOwnerId){
        return repository.findByLandDetailsIdAndLandOwnerId(landDetailsId, landOwnerId);
    }

    @Override
    public List<LandDetailsLandOwners> findByLandOwnerId(Long landOwnerId) {
        return repository.findByLandOwnerId(landOwnerId);
    }
}

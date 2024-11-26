package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.Domain.LandDetailsLandOwners;
import com.example.aurigraph.farmers.Repository.LandDetailsLandOwnersRepository;
import com.example.aurigraph.farmers.Service.LandDetailsLandOwnersService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public LandDetailsLandOwners update(Long id, LandDetailsLandOwners entity) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }
        entity.setId(id); // Ensure the entity has the correct ID
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }
        repository.deleteById(id);
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
}

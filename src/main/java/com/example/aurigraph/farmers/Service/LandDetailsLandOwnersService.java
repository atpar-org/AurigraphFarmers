package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.Domain.LandDetailsLandOwners;
import com.example.aurigraph.farmers.Domain.LandOwner;

import java.util.List;
import java.util.Optional;

public interface LandDetailsLandOwnersService {

    LandDetailsLandOwners save(LandDetailsLandOwners entity);

    LandDetailsLandOwners update(Long id, LandDetailsLandOwners entity);

    void delete(Long id);

    Optional<LandDetailsLandOwners> getById(Long id);

    List<LandDetailsLandOwners> getAll();

    List<LandDetailsLandOwners> findByLandDetailsId(Long id);
}

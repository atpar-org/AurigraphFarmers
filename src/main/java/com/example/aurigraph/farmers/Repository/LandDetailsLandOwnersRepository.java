package com.example.aurigraph.farmers.Repository;


import com.example.aurigraph.farmers.Domain.LandDetailsLandOwners;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandDetailsLandOwnersRepository extends CrudRepository<LandDetailsLandOwners, Long> {

    List<LandDetailsLandOwners> findByLandDetailsId(Long landDetailsId);

    // Additional query methods (if needed) can be defined here
}

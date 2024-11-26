package com.example.aurigraph.farmers.Repository;



import com.example.aurigraph.farmers.Domain.Witness;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WitnessRepository extends CrudRepository<Witness, Long> {

    List<Witness> findByLandDetailsId(Long landDetailsId);
}

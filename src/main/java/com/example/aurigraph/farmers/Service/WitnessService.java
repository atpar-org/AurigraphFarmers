package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.Domain.Witness;

import java.util.List;
import java.util.Optional;

public interface WitnessService {
    List<Witness> findAll();

    Optional<Witness> findById(Long id);

    Witness save(Witness witness);

    Optional<Witness> update(Long id, Witness updatedWitness);

    boolean delete(Long id);

    List<Witness> findByLandDetailsId(Long landDetailsId);
}

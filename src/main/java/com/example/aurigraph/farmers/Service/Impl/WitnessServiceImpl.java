package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.Domain.Witness;
import com.example.aurigraph.farmers.Repository.WitnessRepository;
import com.example.aurigraph.farmers.Service.WitnessService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class WitnessServiceImpl implements WitnessService {

    private final WitnessRepository witnessRepository;

    public WitnessServiceImpl(WitnessRepository witnessRepository) {
        this.witnessRepository = witnessRepository;
    }
    @Override
    public List<Witness> findAll() {
        return (List<Witness>) witnessRepository.findAll();
    }
    @Override
    public Optional<Witness> findById(Long id) {
        return witnessRepository.findById(id);
    }
    @Override
    public Witness save(Witness witness) {
        return witnessRepository.save(witness);
    }
    @Override
    public Optional<Witness> update(Long id, Witness updatedWitness) {
        return witnessRepository.findById(id)
                .map(existing -> {
                    existing.setId(updatedWitness.getId());
                    existing.setName(updatedWitness.getName());
                    existing.setAddress(updatedWitness.getAddress());
                    existing.setNote(updatedWitness.getNote());
                    existing.setDate(updatedWitness.getDate());
                    return witnessRepository.save(existing);
                });
    }
    @Override
    public boolean delete(Long id) {
        if (witnessRepository.existsById(id)) {
            witnessRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Witness> findByLandDetailsId(Long landDetailsId) {
        return witnessRepository.findByLandDetailsId(landDetailsId);
    }

}

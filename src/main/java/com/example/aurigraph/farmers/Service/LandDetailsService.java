package com.example.aurigraph.farmers.Service;


import com.example.aurigraph.farmers.DTO.CompleteLandDetailsDTO;
import com.example.aurigraph.farmers.Domain.LandDetails;

import java.util.List;
import java.util.Optional;

public interface LandDetailsService {
    List<CompleteLandDetailsDTO> findAll();

    CompleteLandDetailsDTO findById(Long id);

    CompleteLandDetailsDTO save(CompleteLandDetailsDTO completeLandDetailsDTO);

    Optional<LandDetails> update(Long id, LandDetails updatedDetails);

    boolean delete(Long id);

    List<CompleteLandDetailsDTO> findByUserId(Integer userId);
}

package com.example.aurigraph.farmers.Service;


import com.example.aurigraph.farmers.DTO.CompleteLandDetailsInDTO;
import com.example.aurigraph.farmers.DTO.CompleteLandDetailsOutDTO;
import com.example.aurigraph.farmers.Domain.LandDetails;

import java.util.List;
import java.util.Optional;

public interface LandDetailsService {
    List<CompleteLandDetailsOutDTO> findAll();

    CompleteLandDetailsOutDTO findById(Long id);

    CompleteLandDetailsOutDTO save(CompleteLandDetailsInDTO completeLandDetailsInDTO);

    Optional<LandDetails> update(Long id, LandDetails updatedDetails);

    boolean delete(Long id);

    List<CompleteLandDetailsOutDTO> findByUserId(Integer userId);
}

package com.example.aurigraph.farmers.Service;


import com.example.aurigraph.farmers.DTO.CompleteLandDetailsInDTO;
import com.example.aurigraph.farmers.DTO.CompleteLandDetailsOutDTO;
import com.example.aurigraph.farmers.Domain.LandDetails;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface LandDetailsService {
//    List<LandDetails> findAll();
    Optional<LandDetails> findById(Long id);
//    LandDetails save(LandDetails landDetails);
    Optional<LandDetails> update(Long id, LandDetails updatedDetails);
    boolean delete(Long id);
//    List<LandDetails> findByUserId(Integer userId);


    List<CompleteLandDetailsOutDTO> findAllCompleteLandDetails(Long userId);

    CompleteLandDetailsOutDTO findCompleteLandDetailsById(Long id);

    CompleteLandDetailsOutDTO saveCompleteLandDetails(CompleteLandDetailsInDTO completeLandDetailsInDTO) throws IOException;

    List<CompleteLandDetailsOutDTO> findCompleteLandDetailsByUserId(Long userId);

    boolean deleteCompleteLandDetails(Long id);
}

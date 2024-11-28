package com.example.aurigraph.farmers.Repository;

import com.example.aurigraph.farmers.Domain.LandDetails;

import org.springframework.data.repository.CrudRepository;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandDetailsRepository extends CrudRepository<LandDetails, Long> {
    List<LandDetails> findLandDetailsByUserId(Integer userId);

}

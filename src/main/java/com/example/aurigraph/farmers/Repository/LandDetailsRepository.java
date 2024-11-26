package com.example.aurigraph.farmers.Repository;

import com.example.aurigraph.farmers.Domain.LandDetails;

import org.springframework.data.repository.CrudRepository;


import org.springframework.stereotype.Repository;

@Repository
public interface LandDetailsRepository extends CrudRepository<LandDetails, Long> {


}

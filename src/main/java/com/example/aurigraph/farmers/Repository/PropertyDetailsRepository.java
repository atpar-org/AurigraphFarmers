package com.example.aurigraph.farmers.Repository;

import com.example.aurigraph.farmers.Domain.PropertyDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyDetailsRepository  extends CrudRepository<PropertyDetails, Long> {

    List<PropertyDetails> findByLandDetailsId(Long findByLandDetailsId);
}

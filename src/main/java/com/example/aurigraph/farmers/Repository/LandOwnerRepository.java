package com.example.aurigraph.farmers.Repository;


import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LandOwnerRepository extends CrudRepository<LandOwner, Long> {


}

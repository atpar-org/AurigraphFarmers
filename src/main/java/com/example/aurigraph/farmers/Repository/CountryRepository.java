package com.example.aurigraph.farmers.Repository;

import com.example.aurigraph.farmers.Domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
}

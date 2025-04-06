package com.example.aurigraph.farmers.Repository;

import com.example.aurigraph.farmers.Domain.Country;
import com.example.aurigraph.farmers.Domain.CountryLevelConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryLevelConfigRepository extends CrudRepository<CountryLevelConfig, Long> {
    List<CountryLevelConfig> findCountryLevelConfigByCountry(Country country);
}

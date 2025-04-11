package com.example.aurigraph.farmers.Repository;


import com.example.aurigraph.farmers.DTO.LocationDTO;
import com.example.aurigraph.farmers.Domain.Location;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository  extends CrudRepository<Location, Long> {
    List<Location> findByLevelConfigId(Long levelConfigId);

    @Query("SELECT new com.example.aurigraph.farmers.DTO.LocationDTO(" +
            "l.id, l.country.id, l.levelConfig.id, " +
            "l.parent.id, l.name, l.code, l.fullPath, l.isCapital) " +
            "FROM Location l " +
            "WHERE (:parentId IS NULL AND l.parent IS NULL) " +
            "   OR (:parentId IS NOT NULL AND l.parent.id = :parentId)")
    List<LocationDTO> findByParentIdAsDTO(@Param("parentId") Long parentId);


    Optional<Location> findByCode(String code);


    List<Location> findByLevelConfigIdAndParentId(Long levelConfigId, Long parentId);
}

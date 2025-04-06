package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.DTO.DynamicLocationResponseDTO;
import com.example.aurigraph.farmers.DTO.LocationDTO;

import com.example.aurigraph.farmers.Domain.Location;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface LocationService {
    Optional<Location> findById(Long locationId);

    List<Location> getByLevelConfigId(Long levelConfigId);

    List<LocationDTO> getByParentId(Long parentId);

    List<Location> getByLevelAndParent(Long levelConfigId, Long parentId);

    void saveLocationFromCsv(Long countryId, Long hierarchyLevelId ,MultipartFile file,String parentLocationLgdCode) throws IOException;

    void saveHierarchicalLocations(Long countryId, MultipartFile file,String locationLgdCode, int startIndex,int endIndex) throws IOException;

    DynamicLocationResponseDTO getDynamicLocationHierarchy(String locationCode);
}

package com.example.aurigraph.farmers.Controller;
import com.example.aurigraph.farmers.DTO.LocationDTO;

import com.example.aurigraph.farmers.Domain.Location;
import com.example.aurigraph.farmers.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

//    @GetMapping("/by-level/{levelConfigId}")
//    public List<Location> getByLevel(@PathVariable Long levelConfigId) {
//        return locationService.getByLevelConfigId(levelConfigId);
//    }

    @GetMapping("/by-parent/{parentId}")
    public List<LocationDTO> getByParent(@PathVariable Long parentId) {
        return locationService.getByParentId(parentId);
    }

//    @GetMapping("/by-level-and-parent")
//    public List<Location> getByLevelAndParent(
//            @RequestParam Long levelConfigId,
//            @RequestParam Long parentId
//    ) {
//        return locationService.getByLevelAndParent(levelConfigId, parentId);
//    }

    @PostMapping(path = "/upload-location-csv" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadLocationCsv(@RequestParam("countryId") Long countryId , @RequestParam("hierarchyLevelId") Long hierarchyLevelId, @RequestParam("file") MultipartFile file,@RequestParam(value = "parentLocationLgdCode", required = false)  String parentLocationLgdCode ) throws IOException {
        try {
            locationService.saveLocationFromCsv(countryId,hierarchyLevelId,file,parentLocationLgdCode);
            return ResponseEntity.ok("Locations uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping(path = "/upload-locations-by-hierarchy-csv",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadLocationsByHierarchyCsv(@RequestParam("countryId") Long countryId , @RequestParam("file") MultipartFile file,@RequestParam("locationLgdCode") String locationLgdCode, @RequestParam("startIndex") int startIndex, @RequestParam("endIndex") int endIndex) throws IOException {

        locationService.saveHierarchicalLocations(countryId,file,locationLgdCode,startIndex,endIndex);
        return ResponseEntity.ok("Locations by Hierarchy imported successfully");
    }

}

package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.DTO.*;

import com.example.aurigraph.farmers.Domain.Country;
import com.example.aurigraph.farmers.Domain.CountryLevelConfig;
import com.example.aurigraph.farmers.Domain.Location;
import com.example.aurigraph.farmers.Repository.CountryLevelConfigRepository;
import com.example.aurigraph.farmers.Repository.CountryRepository;
import com.example.aurigraph.farmers.Repository.LocationRepository;
import com.example.aurigraph.farmers.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryLevelConfigRepository countryLevelConfigRepository;

    @Override
    public Optional<Location> findById(Long locationId) {
        return locationRepository.findById(locationId);
    }

    @Override
    public List<Location> getByLevelConfigId(Long levelConfigId) {
        return locationRepository.findByLevelConfigId(levelConfigId);
    }
    @Override
    public List<LocationDTO> getByParentId(Long parentId) {
        if(parentId<1){
            parentId=null;
        }
        List<LocationDTO> locationDTOS = locationRepository.findByParentIdAsDTO(parentId);
        return locationDTOS;
    }
    @Override
    public List<Location> getByLevelAndParent(Long levelConfigId, Long parentId) {
        return locationRepository.findByLevelConfigIdAndParentId(levelConfigId, parentId);
    }
    @Override
    public void saveLocationFromCsv(Long countryId,Long hierarchyLevelId,MultipartFile file, String parentLocationLgdCode) throws IOException {
        List<LocationPointDTO> locationPointDTOS = parseLocationsCsv(file);

        Optional<Country> country =countryRepository.findById(countryId);
        Optional<CountryLevelConfig> countryLevelConfig =  countryLevelConfigRepository.findById(hierarchyLevelId);
        Location parentLocation = null;
        if(parentLocationLgdCode!=null){
            Optional<Location> parentLocationOpt = locationRepository.findByCode(parentLocationLgdCode);
            if(parentLocationOpt.isEmpty()){
                throw new RuntimeException("Parent location not found");
            }
            parentLocation = parentLocationOpt.get();

        }

        if(country.isPresent() && countryLevelConfig.isPresent()){
            for (LocationPointDTO locationPointDTO : locationPointDTOS) {
                Location location = new Location();

                    location.setCountry(country.get()); // assuming India
                    location.setLevelConfig(countryLevelConfig.get()); // assuming level 1 = State
                    location.setName(locationPointDTO.getLocationName());
                    location.setCode(locationPointDTO.getLocationCode());
                    location.setParent(parentLocation);

                    locationRepository.save(location);
                }
        }else{
            throw new RuntimeException("Country or CountryLevelConfig or ParentLocation not found");
        }

        }

    @Override
    public void saveHierarchicalLocations(Long countryId,MultipartFile file, String locationLgdCode, int startIndex,int endIndex) throws IOException {
        List<LocationDataDTO> dataList = parseLocationCsv(file,startIndex,endIndex);

        Optional<Country> countryOpt = countryRepository.findById(countryId);
        if (countryOpt.isEmpty()) {
            throw new RuntimeException("Country not found");
        }
        Country country = countryOpt.get();
        Optional<Location> givenLevelLocation = locationRepository.findByCode(locationLgdCode);

        if(givenLevelLocation.isEmpty())
        {
            throw new RuntimeException("Given location not found");
        }
        Optional<CountryLevelConfig> givenLevelConfig =  countryLevelConfigRepository.findById(givenLevelLocation.get().getLevelConfig().getId());
        if(givenLevelConfig.isEmpty()){
            throw new RuntimeException("Given level config not found");
        }

        List<CountryLevelConfig> countryLevelConfigs = countryLevelConfigRepository.findCountryLevelConfigByCountry(countryOpt.get());
        countryLevelConfigs.sort(Comparator.comparing(CountryLevelConfig::getLevelOrder));
        List<CountryLevelConfig> DataCountryLevelConfigs = new ArrayList<>();
        for(CountryLevelConfig countryLevelConfig : countryLevelConfigs){
            if(countryLevelConfig.getLevelOrder().compareTo(givenLevelConfig.get().getLevelOrder())>0){
                DataCountryLevelConfigs.add(countryLevelConfig);
            }
        }




//        CountryLevelConfig districtLevel = countryLevelConfigRepository.findById(2L).orElseThrow();
//        CountryLevelConfig subDistrictLevel = countryLevelConfigRepository.findById(3L).orElseThrow();
//        CountryLevelConfig villageLevel = countryLevelConfigRepository.findById(4L).orElseThrow();


        for (LocationDataDTO locationDataDTO : dataList) {
            Location parentLocation = givenLevelLocation.get();
            for(int i=0; i<DataCountryLevelConfigs.size(); i++ ){

               Optional<Location> existedLocationOPt = locationRepository.findByCode(locationDataDTO.getLocationPointDTOs().get(i).getLocationCode());
               if(existedLocationOPt.isEmpty()){
                   Location d = new Location();
                   d.setCountry(country);
                   d.setLevelConfig(DataCountryLevelConfigs.get(i));
                   d.setName(locationDataDTO.getLocationPointDTOs().get(i).getLocationName());
                   d.setCode(locationDataDTO.getLocationPointDTOs().get(i).getLocationCode());
                   d.setParent(parentLocation);
                   parentLocation= locationRepository.save(d);

               }
               else{
                   parentLocation = existedLocationOPt.get();
               }


            }
        }

//            for (LocationDataDTO row : dataList) {
//                // Save District
//
//
//                Location district = locationRepository.findByCode(row.getDistrictCode())
//                        .orElseGet(() -> {
//                            Location d = new Location();
//                            d.setCountry(country);
//                            d.setLevelConfig(districtLevel);
//                            d.setName(row.getDistrictName());
//                            d.setCode(row.getDistrictCode());
//                            d.setParent(state.get());
//                            return locationRepository.save(d);
//                        });
//
//                // Save Sub-District
//                Location subDistrict = locationRepository.findByCode(row.getSubDistrictCode())
//                        .orElseGet(() -> {
//                            Location sd = new Location();
//                            sd.setCountry(country);
//                            sd.setLevelConfig(subDistrictLevel);
//                            sd.setName(row.getSubDistrictName());
//                            sd.setCode(row.getSubDistrictCode());
//                            sd.setParent(district);
//                            return locationRepository.save(sd);
//                        });
//
//                // Save Village
//                locationRepository.findByCode(row.getVillageCode())
//                        .orElseGet(() -> {
//                            Location v = new Location();
//                            v.setCountry(country);
//                            v.setLevelConfig(villageLevel);
//                            v.setName(row.getVillageName());
//                            v.setCode(row.getVillageCode());
//                            v.setParent(subDistrict);
//                            return locationRepository.save(v);
//                        });
//            }



    }

    @Override
    public DynamicLocationResponseDTO getDynamicLocationHierarchy(String locationCode) {
        Optional<Location> locationOpt = locationRepository.findByCode(locationCode);
        if (locationOpt.isEmpty()) throw new RuntimeException("Location not found");

        Location location = locationOpt.get();
        Country country = location.getCountry();

        // Prepare response
        DynamicLocationResponseDTO response = new DynamicLocationResponseDTO();
        response.setCountryId(country.getId());
        response.setCountryName(country.getName());

        DynamicLocationNodeDTO currentNode = null;
        Location currentLocation = location;

        while (currentLocation != null) {
            DynamicLocationNodeDTO newNode = new DynamicLocationNodeDTO();
            newNode.setId(currentLocation.getId());
            newNode.setName(currentLocation.getName());
            newNode.setCode(currentLocation.getCode());
            newNode.setLevelName(currentLocation.getLevelConfig().getLevelName());
            newNode.setChild(currentNode); // nest lower level inside higher one
            currentNode = newNode;
            currentLocation = currentLocation.getParent();
        }

        response.setLocation(currentNode); // top-most location (e.g., State)
        return response;
    }



    private List<LocationPointDTO> parseLocationsCsv(MultipartFile file) throws IOException {
        List<LocationPointDTO> locationPointDTOs = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        boolean isFirst = true;

        while ((line = reader.readLine()) != null) {
            if (isFirst) {
                isFirst = false;
                continue;
            }

            String[] tokens = line.split(",", -1);
            if (tokens.length >= 3) {
                String locationCode = tokens[1].trim();
                String locationName = tokens[2].trim();
                if(locationCode.isBlank() && locationName.isBlank()){
                    break;
                }
                locationPointDTOs.add(new LocationPointDTO(locationCode, locationName));
            }
        }

        // ✅ Sort by stateCode (as integer if needed)
        locationPointDTOs.sort(Comparator.comparingInt(dto -> Integer.parseInt(dto.getLocationCode())));

        return locationPointDTOs;
    }

    private List<LocationDataDTO> parseLocationCsv(MultipartFile file, int startIndex,int endIndex) throws IOException {
        List<LocationDataDTO> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        boolean isFirst = true;

        while ((line = reader.readLine()) != null) {
            if (isFirst) {
                isFirst = false;
                continue;
            }
            String[] tokens = line.split(",", -1); // or tab based on file
            if (tokens.length >= endIndex) {
                LocationDataDTO dto = new LocationDataDTO();
                List<LocationPointDTO> locationPointDTOS = new ArrayList<>();
                for (int i = startIndex; i < endIndex; i++) {
                    if(tokens[i].isBlank() && tokens[i+1].isBlank()){
                        break;
                    }
                    LocationPointDTO locationPointDTO = new LocationPointDTO( tokens[i].trim(),tokens[i+1].trim());
                    locationPointDTOS.add(locationPointDTO);
                    i++;
                }
                dto.setLocationPointDTOs(locationPointDTOS);
                list.add(dto);
            }

        }
        return list;
    }


}

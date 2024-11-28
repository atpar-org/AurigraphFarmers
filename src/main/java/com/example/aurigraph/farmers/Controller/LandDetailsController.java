package com.example.aurigraph.farmers.Controller;

import com.example.aurigraph.farmers.DTO.CompleteLandDetailsDTO;
import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Repository.UserRepository;
import com.example.aurigraph.farmers.Security.SecurityUtils;
import com.example.aurigraph.farmers.Service.LandDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/land-details")
public class LandDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(LandDetailsController.class);

    private final LandDetailsService landDetailsService;
    private final UserRepository userRepository;

    public LandDetailsController(LandDetailsService landDetailsService, UserRepository userRepository) {
        this.landDetailsService = landDetailsService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<CompleteLandDetailsDTO>> getAllLandDetails() {
        logger.info("Request received to fetch all land details");
        try {
            List<CompleteLandDetailsDTO> landDetails = landDetailsService.findAll();
            logger.info("Fetched {} land details successfully", landDetails.size());
            return ResponseEntity.ok(landDetails);
        } catch (Exception e) {
            logger.error("Error fetching all land details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<CompleteLandDetailsDTO>> getAllLandDetailsByUser(@PathVariable int userId) {
        logger.info("Request received to fetch all land details");
        try {
            List<CompleteLandDetailsDTO> landDetails = landDetailsService.findByUserId(userId);
            logger.info("Fetched {} land details successfully", landDetails.size());
            if (!landDetails.isEmpty()) {
                return ResponseEntity.ok(landDetails);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            logger.error("Error fetching all land details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<CompleteLandDetailsDTO> getLandDetailsById(@PathVariable Long id) {
        logger.info("Request received to fetch land details for ID: {}", id);

        try {
            CompleteLandDetailsDTO completeLandDetailsDTO = landDetailsService.findById(id);
            if (completeLandDetailsDTO != null) {
                String currentUser = SecurityUtils.getCurrentUserLogin();
                if (completeLandDetailsDTO.getUserId() != null) {
                    User user =userRepository.findByEmail(currentUser).orElse(null);
                    if (user != null && Objects.equals(completeLandDetailsDTO.getUserId(), user.getId())) {
                        logger.info("Fetched land details for ID: {}", id);
                        return ResponseEntity.ok(completeLandDetailsDTO);
                    }
                }
                return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else {
                logger.warn("Land details not found for ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error fetching land details for ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<CompleteLandDetailsDTO> createLandDetails(@RequestBody CompleteLandDetailsDTO completeLandDetailsDTO) {
        logger.info("Request received to create land details");
        try {
            CompleteLandDetailsDTO savedLandDetails = landDetailsService.save(completeLandDetailsDTO);
            logger.info("Land details created successfully with ID: {}", savedLandDetails.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLandDetails);
        } catch (Exception e) {
            logger.error("Error creating land details: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // You can add more endpoints with similar logging and error handling patterns if required.
}

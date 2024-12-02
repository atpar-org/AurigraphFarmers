package com.example.aurigraph.farmers.Controller;

import com.example.aurigraph.farmers.DTO.CompleteLandDetailsDTO;
import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Repository.UserRepository;
import com.example.aurigraph.farmers.Response.ResponseVO;
import com.example.aurigraph.farmers.Security.SecurityUtils;
import com.example.aurigraph.farmers.Service.LandDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
    public ResponseVO<CompleteLandDetailsDTO> getAllLandDetails() {
        logger.info("Request received to fetch all land details");
        ResponseVO<CompleteLandDetailsDTO> responseVO = new ResponseVO<>();
        try {
            List<CompleteLandDetailsDTO> landDetails = landDetailsService.findAll();
            if (landDetails.isEmpty()) {
                responseVO.setStatus(404);
                responseVO.setMessage("No land details found.");
            } else {
                responseVO.setStatus(200);
                responseVO.setMessage("Land details retrieved successfully.");
                responseVO.setData(landDetails);
            }
            logger.info("Fetched {} land details successfully", landDetails.size());
        } catch (Exception e) {
            logger.error("Error fetching all land details: {}", e.getMessage(), e);
            responseVO.setStatus(500);
            responseVO.setMessage("An error occurred while fetching land details: " + e.getMessage());
        }
        return responseVO;
    }

    @GetMapping("/by-user/{userId}")
    public ResponseVO<CompleteLandDetailsDTO> getAllLandDetailsByUser(@PathVariable int userId) {
        logger.info("Request received to fetch land details for user ID: {}", userId);
        ResponseVO<CompleteLandDetailsDTO> responseVO = new ResponseVO<>();
        List<CompleteLandDetailsDTO> resultList = new ArrayList<>();
        try {
            List<CompleteLandDetailsDTO> landDetails = landDetailsService.findByUserId(userId);
            if (landDetails!= null) {
                String currentUser = SecurityUtils.getCurrentUserLogin();
                User user = userRepository.findByEmail(currentUser).orElse(null);
                if (user != null && userId == user.getId()) {
                    resultList.addAll(landDetails);
                    responseVO.setStatus(200);
                    responseVO.setMessage("Land details retrieved successfully for user ID: " + userId);
                    responseVO.setData(resultList);
                } else {
                    responseVO.setStatus(403);
                    responseVO.setMessage("Access denied for user ID: " + userId);
                }
            } else {
                responseVO.setStatus(404);
                responseVO.setMessage("No land details found for user ID: " + userId);
            }
        } catch (Exception e) {
            logger.error("Error fetching land details for user ID {}: {}", userId, e.getMessage(), e);
            responseVO.setStatus(500);
            responseVO.setMessage("An error occurred while fetching land details: " + e.getMessage());
        }
        return responseVO;
    }


    @GetMapping("/{id}")
    public ResponseVO<CompleteLandDetailsDTO> getLandDetailsById(@PathVariable Long id) {
        logger.info("Request received to fetch land details for ID: {}", id);
        ResponseVO<CompleteLandDetailsDTO> responseVO = new ResponseVO<>();
        List<CompleteLandDetailsDTO> resultList = new ArrayList<>();
        try {
            CompleteLandDetailsDTO completeLandDetailsDTO = landDetailsService.findById(id);
            if (completeLandDetailsDTO != null) {
                String currentUser = SecurityUtils.getCurrentUserLogin();
                User user = userRepository.findByEmail(currentUser).orElse(null);
                if (user != null && Objects.equals(completeLandDetailsDTO.getUserId(), user.getId())) {
                    resultList.add(completeLandDetailsDTO);
                    responseVO.setStatus(200);
                    responseVO.setMessage("Land details retrieved successfully for ID: " + id);
                    responseVO.setData(resultList);
                } else {
                    responseVO.setStatus(403);
                    responseVO.setMessage("Access denied for ID: " + id);
                }
            } else {
                responseVO.setStatus(404);
                responseVO.setMessage("Land details not found for ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error fetching land details for ID {}: {}", id, e.getMessage(), e);
            responseVO.setStatus(500);
            responseVO.setMessage("An error occurred while fetching land details: " + e.getMessage());
        }
        return responseVO;
    }


    @PostMapping
    public ResponseVO<CompleteLandDetailsDTO> createLandDetails(@RequestBody CompleteLandDetailsDTO completeLandDetailsDTO) {
        logger.info("Request received to create land details");
        ResponseVO<CompleteLandDetailsDTO> responseVO = new ResponseVO<>();
        List<CompleteLandDetailsDTO> resultList = new ArrayList<>();

        try {
            CompleteLandDetailsDTO savedLandDetails = landDetailsService.save(completeLandDetailsDTO);
            resultList.add(savedLandDetails);
            responseVO.setStatus(201);
            responseVO.setMessage("Land details created successfully.");
            responseVO.setData(resultList);
            logger.info("Land details created successfully with ID: {}", savedLandDetails.getId());

        } catch (IllegalArgumentException e) {
            logger.error("Bad request: Invalid data provided: {}", e.getMessage(), e);
            responseVO.setStatus(400);
            responseVO.setMessage("Bad Request: " + e.getMessage());
        }  catch (Exception e) {
            logger.error("Error creating land details: {}", e.getMessage(), e);
            responseVO.setStatus(500);
            responseVO.setMessage("An error occurred while creating land details: " + e.getMessage());
        }
        return responseVO;
    }

    // Exception handler for invalid JSON format
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseVO<Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
        logger.error("Invalid JSON format: {}", ex.getMessage());
        ResponseVO<Object> responseVO = new ResponseVO<>();
        responseVO.setStatus(400);
        responseVO.setMessage("Bad Request: Invalid JSON format");
        responseVO.setData(null);
        return new ResponseEntity<>(responseVO, HttpStatus.BAD_REQUEST);
    }
    // You can add more endpoints with similar logging and error handling patterns if required.
}








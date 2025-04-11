package com.example.aurigraph.farmers.Controller;


import com.example.aurigraph.farmers.DTO.LandOwnerDTO;
import com.example.aurigraph.farmers.DTO.LandOwnerWithDocs;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Response.ResponseVO;
import com.example.aurigraph.farmers.Service.LandOwnerService;
import jakarta.persistence.Access;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("landowner")
public class LandOwnersController {

    private static final Logger logger = LoggerFactory.getLogger(LandOwnersController.class);

    @Autowired
    private LandOwnerService landOwnerService;


//    @PostMapping
//    public ResponseVO<LandOwnerWithDocs> createLandOwner(@RequestBody LandOwnerDTO landOwnerDTO) throws IOException {
//
//        logger.info("LandOwnersController.createLandOwner landOwnerDTO: {}", landOwnerDTO);
//        ResponseVO<LandOwnerWithDocs> responseVO = new ResponseVO();
//        if (landOwnerDTO.getMobile() == null) {
//            responseVO.setStatus(400);
//            responseVO.setMessage("Mobile number is required");
//            return responseVO;
//        }
//        LandOwnerWithDocs landOwnerWithDocs = landOwnerService.saveLandOwner(landOwnerDTO);
//
//        if (landOwnerWithDocs == null) {
//            responseVO.setStatus(500);
//            responseVO.setMessage("Failed to save landOwner");
//            return responseVO;
//        }
//
//        responseVO.setStatus(200);
//        responseVO.setMessage("Successfully saved landOwner");
//        responseVO.setData(Collections.singletonList(landOwnerWithDocs));
//        return responseVO;
//
//
//
//    }

    @GetMapping("/{id}")
    public ResponseVO<LandOwner> getLandOwner(@PathVariable Long id) {
        logger.info("Request received to get LandOwner");
        ResponseVO<LandOwner> responseVO = new ResponseVO<>();
        if(id == null) {
            responseVO.setStatus(400);
            responseVO.setMessage("Invalid LandOwner ID");
            return responseVO;
        }
        LandOwner landOwner = landOwnerService.findById(id).orElse(null);
        if (landOwner == null) {
            responseVO.setStatus(404);
            responseVO.setMessage("LandOwner not found");
            return responseVO;
        }
        responseVO.setStatus(200);
        responseVO.setData(Collections.singletonList(landOwner));
        return responseVO;
    }

    @GetMapping
    public ResponseVO<LandOwner> getAllLandOwner() {
        logger.info("Request received to get LandOwners");
        ResponseVO<LandOwner> responseVO = new ResponseVO<>();

        List<LandOwner> landOwners = landOwnerService.findAll();
        if (landOwners == null) {
            responseVO.setStatus(500);
            responseVO.setMessage("Failed to get LandOwners");
            return responseVO;
        }
        responseVO.setStatus(200);
        responseVO.setData(landOwners);
        return responseVO;
    }

    @DeleteMapping("/{id}")
    public ResponseVO delete(@PathVariable Long id) {
        logger.info("Request received to delete LandOwner");
        ResponseVO responseVO = new ResponseVO();

        if(landOwnerService.findById(id).isEmpty()){
            responseVO.setStatus(404);
            responseVO.setMessage("LandOwner not found");
            return responseVO;
        }
        boolean deleted = landOwnerService.deleteLandOwner(id);
        if (deleted) {
            responseVO.setStatus(200);
            responseVO.setMessage("Deleted LandOwner");
            return responseVO;
        }
        responseVO.setStatus(500);
        responseVO.setMessage("Error deleting LandOwner");
        return responseVO;
    }

}

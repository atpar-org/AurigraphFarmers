package com.example.aurigraph.farmers.Controller;


import com.example.aurigraph.farmers.Response.ResponseVO;
import com.example.aurigraph.farmers.Service.LandOwnerService;
import jakarta.persistence.Access;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;

@RestController
@RequestMapping("landowner")
public class LandOwnersController {

    private static final Logger logger = LoggerFactory.getLogger(LandOwnersController.class);

    @Autowired
    private LandOwnerService landOwnerService;

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

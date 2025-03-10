package com.example.aurigraph.farmers.Controller;


import com.example.aurigraph.farmers.Domain.Witness;
import com.example.aurigraph.farmers.Response.ResponseVO;
import com.example.aurigraph.farmers.Service.WitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/witness")
public class WitnessController {

    @Autowired
    private WitnessService witnessService;

    @DeleteMapping("/{id}")
    public ResponseVO delete(@PathVariable Long id) {

        ResponseVO responseVO = new ResponseVO();

        Witness witness = witnessService.findById(id).orElse(null);
        if(witness == null) {
            responseVO.setStatus(404);
            responseVO.setMessage("Witness not found");
            return responseVO;
        }
        boolean deleted = witnessService.delete(id);
        if(deleted) {
            responseVO.setStatus(200);
            responseVO.setMessage("Witness deleted");
            return responseVO;
        }
        responseVO.setStatus(500);
        responseVO.setMessage("Failed to delete witness");
        return responseVO;


    }
}


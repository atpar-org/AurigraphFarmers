//package com.example.aurigraph.farmers.Controller;
//
//
//import com.example.aurigraph.farmers.Domain.PropertyDetails;
//import com.example.aurigraph.farmers.Response.ResponseVO;
//import com.example.aurigraph.farmers.Service.PropertyDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/property")
//public class PropertyController {
//
//    @Autowired
//    private PropertyDetailsService propertyDetailsService;
//
//    @DeleteMapping("/{id}")
//    public ResponseVO delete(@PathVariable Long id) {
//        ResponseVO responseVO = new ResponseVO();
//
//        PropertyDetails propertyDetails = propertyDetailsService.findById(id).orElse(null);
//        if (propertyDetails == null) {
//            responseVO.setStatus(404);
//            responseVO.setMessage("No such property");
//            return responseVO;
//        }
//
//        boolean deleted = propertyDetailsService.delete(id);
//        if (deleted) {
//            responseVO.setStatus(200);
//            responseVO.setMessage("Property deleted successfully");
//            return responseVO;
//        }
//        responseVO.setStatus(500);
//        responseVO.setMessage("Failed to delete property");
//        return responseVO;
//    }
//}

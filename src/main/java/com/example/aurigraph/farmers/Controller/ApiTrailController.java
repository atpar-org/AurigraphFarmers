package com.example.aurigraph.farmers.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTrailController {
    @GetMapping("/apisetuauth")
    public ResponseEntity<String> handleRedirect(@RequestParam(required = false) String code,
                                                 @RequestParam(required = false) String state,
                                                 @RequestParam(required = false) String error,
                                                 @RequestParam(required = false, name = "error_description") String errorDescription) {
        if (error != null) {
            System.out.println("Error: " + error);
            System.out.println("Error Description: " + errorDescription);
            return ResponseEntity.badRequest().body("Error occurred: " + errorDescription);
        }

        System.out.println("Authorization Code: " + code);
        System.out.println("State: " + state);

        return ResponseEntity.ok("Received code: " + code + ", state: " + state);
    }
}

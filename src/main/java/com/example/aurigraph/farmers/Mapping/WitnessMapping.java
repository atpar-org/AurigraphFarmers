package com.example.aurigraph.farmers.Mapping;

import com.example.aurigraph.farmers.Domain.Witness;
import com.example.aurigraph.farmers.Service.WitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class WitnessMapping {

    @Autowired
    private WitnessService witnessService;  // Assuming you have a service to fetch Witness data

    public List<Witness> getWitnesses(List<Witness> givenWitnesses) {
        // List to store modified witness objects
        List<Witness> witnesses = new ArrayList<>();

        // Iterate through each Witness object in the given list
        for (Witness witness : givenWitnesses) {
            Witness existingWitness = null;

            // If witness id is not null, attempt to fetch the existing witness from the database
            if (witness.getId() != null) {
                existingWitness = witnessService.findById(witness.getId()).orElse(null);
            }

            // If no existing witness found, create a new one
            if (existingWitness == null) {
                existingWitness = new Witness();
            }

            // Check if name is not null and update if needed
            if (witness.getName() != null) {
                existingWitness.setName(witness.getName());  // Set the name if not null
            }

            // Check if address is not null and update if needed
            if (witness.getAddress() != null) {
                existingWitness.setAddress(witness.getAddress());  // Set address if not null
            }

            // Check if note is not null and update if needed
            if (witness.getNote() != null) {
                existingWitness.setNote(witness.getNote());  // Set note if not null
            }

            // Check if date is not null and update if needed
            if (witness.getDate() != null) {
                existingWitness.setDate(witness.getDate());  // Set date if not null
            }

            // Check if landDetailsId is not null and update if needed
            if (witness.getLandDetailsId() != null) {
                existingWitness.setLandDetailsId(witness.getLandDetailsId());  // Set landDetailsId if not null
            }

            // Add the processed witness to the list
            witnesses.add(existingWitness);
        }

        // Return the modified list
        return witnesses;
    }
}

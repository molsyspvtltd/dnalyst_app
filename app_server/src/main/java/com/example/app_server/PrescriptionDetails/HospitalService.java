package com.example.app_server.PrescriptionDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    // Method to add a new hospital
    public String addHospital(String name, String address) {
        Optional<Hospital> existingHospital = hospitalRepository.findByName(name);
        if (existingHospital.isPresent()) {
            return "Hospital with the same name already exists!";
        }

        Hospital hospital = new Hospital(name, address);
        hospitalRepository.save(hospital);
        return "Hospital added successfully!";
    }

    // Method to get all hospitals
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    // Method to get a hospital by ID
    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with ID: " + id));
    }
}

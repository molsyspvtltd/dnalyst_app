package com.example.app_server.PrescriptionDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // Endpoint to add a new hospital
    @PostMapping("/add")
    public String addHospital(@RequestBody Hospital hospital) {
        return hospitalService.addHospital(hospital.getName(), hospital.getAddress());
    }

    // Endpoint to get all hospitals
    @GetMapping("/all")
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    // Endpoint to get a specific hospital by ID
    @GetMapping("/{id}")
    public Hospital getHospitalById(@PathVariable Long id) {
        return hospitalService.getHospitalById(id);
    }
}

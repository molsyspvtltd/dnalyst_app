package com.example.app_server.PrescriptionDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    // Endpoint to upload a prescription file for a user
    @PostMapping("/add")
    public String addPrescription(@RequestParam String userId, @RequestParam("file") MultipartFile file) {
        return prescriptionService.addPrescription(userId, file);
    }

    // Endpoint to retrieve all prescriptions
    @GetMapping("/all")
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    // Endpoint to retrieve prescriptions for a specific user
    @GetMapping("/user/{mrnId}")
    public List<Prescription> getPrescriptionsByUserId(@PathVariable String userId) {
        return prescriptionService.getPrescriptionsByUserId(userId);
    }
}

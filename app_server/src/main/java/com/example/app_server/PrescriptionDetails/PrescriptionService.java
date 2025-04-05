package com.example.app_server.PrescriptionDetails;

import com.example.app_server.UserAccountCreation.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PrescriptionService {

    private final String UPLOAD_DIR = "uploads/prescription/";

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to add a prescription for an existing user
    public String addPrescription(String mrnId, MultipartFile file) {
        userRepository.findById(mrnId)
                .orElseThrow(() -> new RuntimeException("User with ID " + mrnId + " does not exist"));

        try {
            // Save the file to the upload directory
            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            File uploadFile = new File(filePath);
            uploadFile.getParentFile().mkdirs(); // Create directories if not exist
            file.transferTo(Paths.get(filePath));

            // Save the prescription record to the database
            Prescription prescription = new Prescription(filePath, userRepository.findById(mrnId).get());
            prescriptionRepository.save(prescription);

            return "Prescription uploaded and assigned successfully!";
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }

    // Method to retrieve all prescriptions
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    // Method to retrieve prescriptions by user ID
    public List<Prescription> getPrescriptionsByUserId(String mrnId) {
        return prescriptionRepository.findByMrnId(mrnId);  // Using custom query from the repository
    }
}

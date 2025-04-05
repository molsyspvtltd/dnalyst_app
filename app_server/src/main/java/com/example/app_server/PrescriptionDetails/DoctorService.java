package com.example.app_server.PrescriptionDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalService hospitalService;

    // Method to add a doctor
    public String addDoctor(Doctor doctor) {
        // Ensure the hospital is set correctly from the incoming doctor object
        if (doctor.getHospital() == null || doctor.getHospital().getId() == null) {
            return "Hospital ID is required for the doctor.";
        }

        // Fetch the hospital by ID if it's not already set in the object
        Hospital hospital = hospitalService.getHospitalById(doctor.getHospital().getId());
        doctor.setHospital(hospital);

        // Save the doctor
        doctorRepository.save(doctor);
        return "Doctor added successfully!";
    }

    // Method to get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Method to get a doctor by ID
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
    }
}

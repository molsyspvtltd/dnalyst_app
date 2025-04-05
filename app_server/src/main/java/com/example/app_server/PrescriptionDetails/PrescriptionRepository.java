package com.example.app_server.PrescriptionDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Custom query to find prescriptions by user ID
    @Query("SELECT p FROM Prescription p WHERE p.user.mrnId = :mrnId")
    List<Prescription> findByMrnId(String mrnId);
}

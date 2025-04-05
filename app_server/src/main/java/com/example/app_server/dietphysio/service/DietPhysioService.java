package com.example.app_server.dietphysio.service;

import com.example.app_server.dietphysio.model.ExerciseDetail;
import com.example.app_server.dietphysio.repository.ExerciseDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietPhysioService {

    @Autowired
    private ExerciseDetailRepository exerciseDetailRepository;

    /**
     * Updates the status of all exercises based on user input.
     * @param status The status to set for all exercises ("Completed", "Partially Completed", "Not Completed").
     */
    public void updateAllExerciseStatus(String status) {
        // Validate the status
        String statusToUpdate = null;
        switch (status.toLowerCase()) {
            case "completed":
                statusToUpdate = "Completed";
                break;
            case "partially completed":
                statusToUpdate = "Partially Completed";
                break;
            case "not completed":
                statusToUpdate = "Not Completed";
                break;
            default:
                System.out.println("Invalid status value: " + status);
                return; // Exit if the status is invalid
        }

        // Fetch all exercises to update
        List<ExerciseDetail> exercises = exerciseDetailRepository.findAll();
        for (ExerciseDetail detail : exercises) {
            detail.setStatus(statusToUpdate); // Set the new status for each exercise
            exerciseDetailRepository.save(detail); // Save the updated exercise detail
        }

        System.out.println("All exercises' statuses updated to: " + statusToUpdate);
    }

    /**
     * Updates the status of a specific exercise based on user input.
     * @param exerciseId The ID of the exercise to update.
     * @param status The new status to set for the exercise ("Completed", "Partially Completed", "Not Completed").
     */
    public void updateExerciseStatus(Long exerciseId, String status) {
        // Validate the status
        String statusToUpdate = null;
        switch (status.toLowerCase()) {
            case "completed":
                statusToUpdate = "Completed";
                break;
            case "partially completed":
                statusToUpdate = "Partially Completed";
                break;
            case "not completed":
                statusToUpdate = "Not Completed";
                break;
            default:
                System.out.println("Invalid status value: " + status);
                return; // Exit if the status is invalid
        }

        // Fetch the exercise detail from the database using exerciseId
        ExerciseDetail detail = exerciseDetailRepository.findById(exerciseId).orElse(null);
        if (detail == null) {
            System.out.println("Exercise not found for ID: " + exerciseId);
            return; // Exit if the exercise is not found
        }

        // Update the status of the exercise detail
        detail.setStatus(statusToUpdate);
        exerciseDetailRepository.save(detail);

        System.out.println("Exercise ID " + exerciseId + " status updated to: " + statusToUpdate);
    }
}

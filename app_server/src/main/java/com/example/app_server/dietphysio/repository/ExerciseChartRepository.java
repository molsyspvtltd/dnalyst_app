package com.example.app_server.dietphysio.repository;

import com.example.app_server.dietphysio.model.ExerciseChart;
import com.example.app_server.UserAccountCreation.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseChartRepository extends JpaRepository<ExerciseChart, Long> {
    Optional<ExerciseChart> findByUserAndChartNumber(User user, int chartNumber);
    Optional<ExerciseChart> findByChartNumber(int chartNumber);
}

package com.example.app_server.dietphysio.repository;

import com.example.app_server.UserAccountCreation.User;
import com.example.app_server.dietphysio.model.DietChart;

import com.example.app_server.dietphysio.model.ExerciseChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DietChartRepository extends JpaRepository<DietChart, Long> {
    Optional<DietChart> findByChartNumber(int chartNumber);
    boolean existsByUser(User user);
    boolean existsByUserAndChartNumber(User user, int chartNumber);
    Optional<DietChart> findByUserAndChartNumber(User user, int chartNumber);
    @Query("SELECT MAX(dc.chartNumber) FROM DietChart dc WHERE dc.user = :user")
    Optional<Integer> findMaxChartNumberByUser(@Param("user") User user);

}

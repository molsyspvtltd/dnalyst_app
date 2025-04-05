package com.example.app_server.dietphysio.repository;

import com.example.app_server.dietphysio.model.ExerciseChart;
import com.example.app_server.dietphysio.model.ExerciseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseDetailRepository extends JpaRepository<ExerciseDetail, Long> {
    List<ExerciseDetail> findByDay(int day);
    Optional<ExerciseDetail> findByExerciseChartAndDayAndExerciseName(ExerciseChart exerciseChart, int day, String exerciseName);
    List<ExerciseDetail> findByExerciseChart_ChartNumber(int chartNumber);
}


package com.example.app_server.dietphysio.service;

import com.example.app_server.dietphysio.model.ExerciseChart;
import com.example.app_server.dietphysio.model.ExerciseDetail;
import com.example.app_server.dietphysio.repository.ExerciseDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseDetailService {

    @Autowired
    private ExerciseDetailRepository exerciseDetailRepository;

    public ExerciseDetail addExerciseDetail(ExerciseChart chart, int day, String category, String exerciseName, String details, String status) {
        ExerciseDetail detail = new ExerciseDetail();
        detail.setExerciseChart(chart);
        detail.setDay(day);
        detail.setCategory(category);
        detail.setExerciseName(exerciseName);
        detail.setDetails(details);
        detail.setStatus(status != null ? status : "Not Completed");
        return exerciseDetailRepository.save(detail);
    }
    public List<ExerciseDetail> getExerciseDetailsByDay(int day) {
        return exerciseDetailRepository.findByDay(day);
    }
    public List<ExerciseDetail> getExerciseDetailsByChartNumber(int chartNumber) {
        return exerciseDetailRepository.findByExerciseChart_ChartNumber(chartNumber);
    }

}

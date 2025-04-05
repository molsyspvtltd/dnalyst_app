package com.example.app_server.dietphysio.service;

import com.example.app_server.dietphysio.model.ExerciseChart;
import com.example.app_server.UserAccountCreation.User;
import com.example.app_server.dietphysio.repository.ExerciseChartRepository;
import com.example.app_server.dietphysio.repository.ExerciseDetailRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseChartService {

    @Autowired
    private ExerciseChartRepository exerciseChartRepository;

    @Autowired
    private ExerciseDetailRepository exerciseDetailRepository;

    public ExerciseChart createExerciseChart(User user, int chartNumber, String startDate, String endDate) {
        ExerciseChart chart = new ExerciseChart();
        chart.setUser(user);
        chart.setChartNumber(chartNumber);
        return exerciseChartRepository.save(chart);
    }

    public void deleteExerciseChartByChartNumber(int chartNumber) {
        // Find the ExerciseChart by chart_number
        ExerciseChart chart = exerciseChartRepository.findByChartNumber(chartNumber)
                .orElseThrow(() -> new IllegalArgumentException("Exercise chart with chart number " + chartNumber + " not found"));

        // Delete the chart (this will also delete associated ExerciseDetails due to cascading)
        exerciseChartRepository.delete(chart);
    }

    public ExerciseChart findExerciseChartByChartNumber(int chartNumber) {
        return exerciseChartRepository.findByChartNumber(chartNumber)
                .orElseThrow(() -> new IllegalArgumentException("Exercise chart with chart number " + chartNumber + " not found"));
    }
}

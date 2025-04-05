package com.example.app_server.dietphysio.dto;


import com.example.app_server.dietphysio.model.ExerciseChart;

public class ExerciseDetailRequest {
    private ExerciseChart exerciseChart;
    private int day;
    private String category;
    private String exerciseName;
    private String details;
    private String status;

    // Getters and setters
    public ExerciseChart getExerciseChart() {
        return exerciseChart;
    }

    public void setExerciseChart(ExerciseChart exerciseChart) {
        this.exerciseChart = exerciseChart;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

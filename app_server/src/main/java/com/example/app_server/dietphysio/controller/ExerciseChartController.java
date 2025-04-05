package com.example.app_server.dietphysio.controller;

import com.example.app_server.dietphysio.dto.CreateChartRequest;
import com.example.app_server.dietphysio.model.ExerciseChart;
import com.example.app_server.dietphysio.service.ExerciseChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercise-charts")
public class ExerciseChartController {

    @Autowired
    private ExerciseChartService exerciseChartService;

    @PostMapping("/create")
    public ResponseEntity<ExerciseChart> createChart(@RequestBody CreateChartRequest request) {
        ExerciseChart chart = exerciseChartService.createExerciseChart(request.getUser(), request.getChartNumber(), request.getStartDate(), request.getEndDate());
        return new ResponseEntity<>(chart, HttpStatus.CREATED);
    }
    @DeleteMapping("/{chartNumber}")
    public ResponseEntity<Void> deleteChart(@PathVariable int chartNumber) {
        exerciseChartService.deleteExerciseChartByChartNumber(chartNumber);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{chartNumber}")
    public ResponseEntity<ExerciseChart> getExerciseChart(@PathVariable int chartNumber) {
        ExerciseChart chart = exerciseChartService.findExerciseChartByChartNumber(chartNumber);
        return new ResponseEntity<>(chart, HttpStatus.OK);
    }
}

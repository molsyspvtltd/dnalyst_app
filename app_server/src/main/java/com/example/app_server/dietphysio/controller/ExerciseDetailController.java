package com.example.app_server.dietphysio.controller;

import com.example.app_server.dietphysio.dto.ExerciseDetailRequest;
import com.example.app_server.dietphysio.model.ExerciseDetail;
import com.example.app_server.dietphysio.service.ExerciseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise-details")
public class ExerciseDetailController {

    @Autowired
    private ExerciseDetailService exerciseDetailService;

    @PostMapping("/add")
    public ResponseEntity<ExerciseDetail> addExerciseDetail(@RequestBody ExerciseDetailRequest request) {
        ExerciseDetail detail = exerciseDetailService.addExerciseDetail(
                request.getExerciseChart(),
                request.getDay(),
                request.getCategory(),
                request.getExerciseName(),
                request.getDetails(),
                request.getStatus()
        );
        return new ResponseEntity<>(detail, HttpStatus.CREATED);
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<ExerciseDetail>> getExerciseDetailsByDay(@PathVariable int day) {
        List<ExerciseDetail> details = exerciseDetailService.getExerciseDetailsByDay(day);
        if (details.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
    @GetMapping("/chart/{chartNumber}")
    public ResponseEntity<List<ExerciseDetail>> getExerciseDetailsByChartNumber(@PathVariable int chartNumber) {
        List<ExerciseDetail> details = exerciseDetailService.getExerciseDetailsByChartNumber(chartNumber);
//        if (details.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}

package com.example.app_server.dietphysio.controller;

import com.example.app_server.dietphysio.dto.DietChartRequest;
import com.example.app_server.dietphysio.model.DietChart;
import com.example.app_server.dietphysio.service.DietChartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diet-charts")
public class DietChartController {

    private final DietChartService dietChartService;

    public DietChartController(DietChartService dietChartService) {
        this.dietChartService = dietChartService;
    }

    @GetMapping("/{chartNumber}")
    public ResponseEntity<DietChart> getDietChart(@PathVariable int chartNumber) {
        DietChart chart = dietChartService.findDietChartByChartNumber(chartNumber);
        return new ResponseEntity<>(chart, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DietChart> createChart(@RequestBody DietChartRequest request) {
        DietChart chart = dietChartService.createDietChart(request.getUser(), request.getChartNumber(), request.getStartDate(), request.getEndDate());
        return new ResponseEntity<>(chart, HttpStatus.CREATED);
    }

    @DeleteMapping("/{chartNumber}")
    public ResponseEntity<Void> deleteChart(@PathVariable int chartNumber) {
        dietChartService.deleteDietChartByChartNumber(chartNumber);
        return ResponseEntity.noContent().build();
    }
}
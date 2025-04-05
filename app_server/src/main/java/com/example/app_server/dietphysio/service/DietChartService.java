package com.example.app_server.dietphysio.service;

import com.example.app_server.UserAccountCreation.User;
import com.example.app_server.dietphysio.model.DietChart;
import com.example.app_server.dietphysio.repository.DietChartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DietChartService {

    private final DietChartRepository dietChartRepository;

    public DietChartService(DietChartRepository dietChartRepository) {
        this.dietChartRepository = dietChartRepository;
    }

    // Retrieve all DietCharts
    public List<DietChart> getAllDietCharts() {
        return dietChartRepository.findAll();
    }

    // Retrieve a specific DietChart by ID
    public Optional<DietChart> getDietChartById(Long id) {
        return dietChartRepository.findById(id);
    }

    // Create a new DietChart
    public DietChart createDietChart(User user, int chartNumber, String startDate, String endDate) {
        DietChart dietChart = new DietChart();
        dietChart.setUser(user);
        dietChart.setChartNumber(chartNumber);
        return dietChartRepository.save(dietChart);
    }

    // Delete a DietChart by ID
    public boolean deleteDietChart(Long id) {
        if (dietChartRepository.existsById(id)) {
            dietChartRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Delete a DietChart by chart number
    public void deleteDietChartByChartNumber(int chartNumber) {
        DietChart chart = dietChartRepository.findByChartNumber(chartNumber)
                .orElseThrow(() -> new IllegalArgumentException("Diet chart with chart number " + chartNumber + " not found"));
        dietChartRepository.delete(chart);
    }

    // Retrieve a DietChart by chart number
    public DietChart findDietChartByChartNumber(int chartNumber) {
        return dietChartRepository.findByChartNumber(chartNumber)
                .orElseThrow(() -> new IllegalArgumentException("Diet chart with chart number " + chartNumber + " not found"));
    }

}

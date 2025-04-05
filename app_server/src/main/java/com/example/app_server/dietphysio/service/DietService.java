package com.example.app_server.dietphysio.service;

import com.example.app_server.UserAccountCreation.User;
import com.example.app_server.UserAccountCreation.UserRepository;
import com.example.app_server.dietphysio.model.DietChart;
import com.example.app_server.dietphysio.model.DietPlan;
import com.example.app_server.dietphysio.model.ExerciseChart;
import com.example.app_server.dietphysio.model.MealType;
import com.example.app_server.dietphysio.repository.DietChartRepository;
import com.example.app_server.dietphysio.repository.DietPlanRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DietService {

    private final UserRepository userRepository;
    private final DietChartRepository dietChartRepository;
    private final DietPlanRepository dietPlanRepository;

    public DietService(UserRepository userRepository, DietChartRepository dietChartRepository, DietPlanRepository dietPlanRepository) {
        this.userRepository = userRepository;
        this.dietChartRepository = dietChartRepository;
        this.dietPlanRepository = dietPlanRepository;
    }

//    public void uploadDietChart(MultipartFile file, String mrnId) throws IOException {
//        // Validate userId
//        User user = userRepository.findByMrnId(mrnId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + mrnId));
//
//        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0);
//
//            // Validate the Excel file structure
//            if (sheet.getRow(0) == null || sheet.getRow(0).getPhysicalNumberOfCells() < 7) {
//                throw new RuntimeException("Invalid Excel format. Please ensure the file has the correct structure.");
//            }
//
//            int dayCounter = 1; // Start with day 1
//            DietChart currentChart = null;
//            String lastDayValue = null; // To handle merged cells in the 'day' column
//
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) continue; // Skip header row
//
//                // Handle merged cells or missing 'day' values
//                String dayCell = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null;
//                if (dayCell != null && !dayCell.trim().isEmpty()) {
//                    lastDayValue = dayCell.trim(); // Update last valid day value
//                } else if (lastDayValue != null) {
//                    dayCell = lastDayValue; // Use the last valid day value
//                } else {
//                    System.out.println("Warning: Missing 'Day' value at row " + (row.getRowNum() + 1) + ". Skipping row.");
//                    continue; // Skip the row if no valid day is found
//                }
//
//                // Calculate chart number based on day counter
////              int chartNumber = (dayCounter - 1) / 15 + 1; // Day 1-15 -> Chart 1, Day 16-30 -> Chart 2, etc.
//                int chartNumber = (int) Math.ceil(dayCounter / 15.0);
//                // If chart number changes, start a new chart
//                // If chart number changes, start a new chart or reuse the existing one
//                if (currentChart == null || chartNumber != currentChart.getChartNumber()) {
//                    if (currentChart != null) {
//                        dietChartRepository.save(currentChart); // Save previous chart
//                    }
//
//                    // Check if the chart already exists for the user, and reuse it if available
//                    currentChart = dietChartRepository.findByUserAndChartNumber(user, chartNumber)
//                            .orElseGet(() -> {
//                                DietChart newChart = new DietChart();
//                                newChart.setUser(user);
//                                newChart.setChartNumber(chartNumber);
//                                return dietChartRepository.save(newChart); // Save and return the new chart
//                            });
//                }
//
//                // Create and save DietPlan for the current day
//                DietPlan dietPlan = new DietPlan();
//                dietPlan.setDietChart(currentChart);
//                dietPlan.setDay(dayCell); // Set the day value from the Excel file
//
//                // Handle other fields with defaults or warnings for missing values
//                String mealTime = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null;
//                dietPlan.setMealTime(mealTime != null && !mealTime.trim().isEmpty() ? MealType.fromString(mealTime) : null);
//
//                dietPlan.setMenu(row.getCell(4) != null ? row.getCell(4).getStringCellValue() : "Default Menu");
//                dietPlan.setRecipe(row.getCell(5) != null ? row.getCell(5).getStringCellValue() : "No Recipe Available");
//                dietPlan.setCaloriesProtein(row.getCell(6) != null ? row.getCell(6).getStringCellValue() : "Unknown");
//
//                try {
//                    dietPlanRepository.save(dietPlan);
//                } catch (Exception e) {
//                    System.out.println("Error saving DietPlan for row " + (row.getRowNum() + 1) + ": " + e.getMessage());
//                }
//
//                // Increment day counter after processing each row
//                dayCounter++;
//            }
//
//            // Save the last chart if there are remaining days
//            if (currentChart != null) {
//                dietChartRepository.save(currentChart);
//            }
//        }
//    }


    public void uploadDietChart(MultipartFile file, String mrnId) throws IOException {
        // Validate user
        User user = userRepository.findByMrnId(mrnId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + mrnId));

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getRow(0) == null || sheet.getRow(0).getPhysicalNumberOfCells() < 7) {
                throw new RuntimeException("Invalid Excel format. Please ensure the file has the correct structure.");
            }

            Map<Integer, DietChart> chartMap = new HashMap<>(); // Store charts by chartNumber
            String lastDayValue = null;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                // Handle merged cells for 'day' values
                String dayCell = row.getCell(2) != null ? row.getCell(2).getStringCellValue().trim() : null;
                if (dayCell != null && !dayCell.isEmpty()) {
                    lastDayValue = dayCell;
                } else if (lastDayValue != null) {
                    dayCell = lastDayValue;
                } else {
                    System.out.println("Warning: Missing 'Day' value at row " + (row.getRowNum() + 1) + ". Skipping row.");
                    continue;
                }

                // Convert dayCell to an integer (Example: "Day 1" -> 1)
                int dayNumber = extractDayNumber(dayCell);
                if (dayNumber == -1) {
                    System.out.println("Warning: Invalid 'Day' value at row " + (row.getRowNum() + 1) + ". Skipping row.");
                    continue;
                }

                // Determine chart number based on the day (1-15 => Chart 1, 16-30 => Chart 2, etc.)
                int chartNumber = (int) Math.ceil(dayNumber / 15.0);

                // Get or create the corresponding DietChart
                DietChart currentChart = chartMap.computeIfAbsent(chartNumber, num ->
                        dietChartRepository.findByUserAndChartNumber(user, num)
                                .orElseGet(() -> createNewDietChart(user, num))
                );

                // Create DietPlan entry
                DietPlan dietPlan = new DietPlan();
                dietPlan.setDietChart(currentChart);
                dietPlan.setDay(dayCell);

                String mealTime = row.getCell(3) != null ? row.getCell(3).getStringCellValue().trim() : null;
                dietPlan.setMealTime(mealTime != null && !mealTime.isEmpty() ? MealType.fromString(mealTime) : null);

                dietPlan.setMenu(row.getCell(4) != null ? row.getCell(4).getStringCellValue().trim() : "Default Menu");
                dietPlan.setRecipe(row.getCell(5) != null ? row.getCell(5).getStringCellValue().trim() : "No Recipe Available");
                dietPlan.setCaloriesProtein(row.getCell(6) != null ? row.getCell(6).getStringCellValue().trim() : "Unknown");

                try {
                    dietPlanRepository.save(dietPlan);
                } catch (Exception e) {
                    System.out.println("Error saving DietPlan for row " + (row.getRowNum() + 1) + ": " + e.getMessage());
                }
            }

            // Save all created charts
            chartMap.values().forEach(dietChartRepository::save);
        }
    }

    /**
     * Extracts the numerical day from a "Day X" string.
     * Example: "Day 1" → 1, "Day 25" → 25
     */
    private int extractDayNumber(String dayCell) {
        try {
            return Integer.parseInt(dayCell.replaceAll("[^0-9]", "")); // Extracts digits
        } catch (NumberFormatException e) {
            return -1; // Invalid format
        }
    }

    private DietChart createNewDietChart(User user, int chartNumber) {
        DietChart dietChart = new DietChart();
        dietChart.setUser(user);
        dietChart.setChartNumber(chartNumber); // Ensure the chart number is stored
        dietChartRepository.save(dietChart);
        return dietChart;
    }

}

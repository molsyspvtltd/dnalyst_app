package com.example.app_server.dietphysio.service;

import com.example.app_server.dietphysio.model.ExerciseChart;
import com.example.app_server.dietphysio.model.ExerciseDetail;
import com.example.app_server.UserAccountCreation.User;
import com.example.app_server.dietphysio.repository.ExerciseChartRepository;
import com.example.app_server.dietphysio.repository.ExerciseDetailRepository;
import com.example.app_server.UserAccountCreation.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;
//
//@Service
//public class ExcelService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ExerciseChartRepository exerciseChartRepository;
//
//    @Autowired
//    private ExerciseDetailRepository exerciseDetailRepository;

//    public void saveExcelData(MultipartFile file) throws Exception {
//        InputStream inputStream = file.getInputStream();
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheetAt(0);
//
//        User user = null;
//        ExerciseChart currentChart = null;
//
//        for (Row row : sheet) {
//            if (row.getRowNum() == 0) continue; // Skip header row
//
//            // Retrieve cell values
//            String clientName = getCellValueAsString(row.getCell(0));
//            String mrnId = getCellValueAsString(row.getCell(1));
//            Integer day = getCellValueAsInteger(row.getCell(2));
//            String category = getCellValueAsString(row.getCell(3));
//            String exerciseName = getCellValueAsString(row.getCell(4));
//            String details = getCellValueAsString(row.getCell(5));
//            String status = getCellValueAsString(row.getCell(6));
//
//            // Skip rows with critical fields missing
//            if (mrnId == null || clientName == null || day == null) {
//                continue;
//            }
//
//            // Create or retrieve the user
//            if (user == null || !user.getMrnId().equals(mrnId)) {
//                user = userRepository.findByMrnId(mrnId).orElseGet(() -> {
//                    User newUser = new User();
//                    newUser.setFirstName(clientName);
//                    newUser.setMrnId(mrnId);
//                    return userRepository.save(newUser);
//                });
//            }
//
//            // Determine the chart number based on the day (divide by 15 and add 1)
//            int chartNumber = (int) Math.ceil(day / 15.0);
//
//            // Retrieve or create a new exercise chart based on the user and calculated chart number
//            Optional<ExerciseChart> existingChart = exerciseChartRepository.findByUserAndChartNumber(user, chartNumber);
//            if (existingChart.isPresent()) {
//                currentChart = existingChart.get();
//            } else {
//                // Create a new chart if it doesn't exist
//                currentChart = new ExerciseChart();
//                currentChart.setUser(user);
//                currentChart.setChartNumber(chartNumber);
//                currentChart = exerciseChartRepository.save(currentChart);
//            }
//            Optional<ExerciseDetail> existingExerciseDetail = exerciseDetailRepository.findByExerciseChartAndDayAndExerciseName(currentChart, day, exerciseName);
//            if (existingExerciseDetail.isPresent()) {
//                // Skip saving this entry if it already exists
//                continue;
//            }
//
//            // Save exercise details for the chart
//            ExerciseDetail exerciseDetail = new ExerciseDetail();
//            exerciseDetail.setExerciseChart(currentChart);
//            exerciseDetail.setDay(day);
//            exerciseDetail.setCategory(category);
//            exerciseDetail.setExerciseName(exerciseName);
//            exerciseDetail.setDetails(details);
//            exerciseDetail.setStatus(status);
//
//            exerciseDetailRepository.save(exerciseDetail);
//        }
//    }
//
//    // Helper methods to retrieve cell values

//    }
//public void saveExcelData(MultipartFile file) throws Exception {
//    InputStream inputStream = file.getInputStream();
//    Workbook workbook = new XSSFWorkbook(inputStream);
//    Sheet sheet = workbook.getSheetAt(0);
//
//    ExerciseChart currentChart = null;
//
//    for (Row row : sheet) {
//        if (row.getRowNum() == 0) continue; // Skip header row
//
//        // Retrieve cell values
//        String clientName = getCellValueAsString(row.getCell(0)); // Not used for setting user
//        String mrnId = getCellValueAsString(row.getCell(1));
//        Integer day = getCellValueAsInteger(row.getCell(2));
//        String category = getCellValueAsString(row.getCell(3));
//        String exerciseName = getCellValueAsString(row.getCell(4));
//        String details = getCellValueAsString(row.getCell(5));
//        String status = getCellValueAsString(row.getCell(6));
//
//        // Skip rows with critical fields missing
//        if (mrnId == null || day == null) {
//            continue;
//        }
//
//        // Check if the user exists in the database
//        Optional<User> userOptional = userRepository.findByMrnId(mrnId);
//        if (userOptional.isEmpty()) {
//            // Log or skip rows where the user does not exist
//            System.err.println("User with MRN ID " + mrnId + " not found. Skipping row.");
//            continue;
//        }
//
//        User user = userOptional.get();
//
//        // Determine the chart number based on the day (divide by 15 and add 1)
//        int chartNumber = (int) Math.ceil(day / 15.0);
//
//        // Retrieve or create a new exercise chart based on the user and calculated chart number
//        Optional<ExerciseChart> existingChart = exerciseChartRepository.findByUserAndChartNumber(user, chartNumber);
//        if (existingChart.isPresent()) {
//            currentChart = existingChart.get();
//        } else {
//            // Create a new chart if it doesn't exist
//            currentChart = new ExerciseChart();
//            currentChart.setUser(user);
//            currentChart.setChartNumber(chartNumber);
//            currentChart = exerciseChartRepository.save(currentChart);
//        }
//
//        // Check if the exercise detail already exists
//        Optional<ExerciseDetail> existingExerciseDetail = exerciseDetailRepository.findByExerciseChartAndDayAndExerciseName(currentChart, day, exerciseName);
//        if (existingExerciseDetail.isPresent()) {
//            // Skip saving this entry if it already exists
//            continue;
//        }
//
//        // Save exercise details for the chart
//        ExerciseDetail exerciseDetail = new ExerciseDetail();
//        exerciseDetail.setExerciseChart(currentChart);
//        exerciseDetail.setDay(day);
//        exerciseDetail.setCategory(category);
//        exerciseDetail.setExerciseName(exerciseName);
//        exerciseDetail.setDetails(details);
//        exerciseDetail.setStatus(status);
//
//        exerciseDetailRepository.save(exerciseDetail);
//    }
//}
//    private String getCellValueAsString(Cell cell) {
//        if (cell == null || cell.getCellType() == CellType.BLANK) {
//            return null;
//        }
//        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.toString();
//    }
//
//    private Integer getCellValueAsInteger(Cell cell) {
//        if (cell == null || cell.getCellType() == CellType.BLANK) {
//            return null;
//        }
//        if (cell.getCellType() == CellType.NUMERIC) {
//            return (int) cell.getNumericCellValue();
//        }
//        return null;
//    }
//}
//
@Service
public class ExcelService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseChartRepository exerciseChartRepository;

    @Autowired
    private ExerciseDetailRepository exerciseDetailRepository;

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    @Transactional
    public ResponseEntity<String> assignExercisesToUser(String mrnId, MultipartFile file) {
        // Fetch the user by MRN ID
        Optional<User> userOptional = userRepository.findByMrnId(mrnId);
        if (userOptional.isEmpty()) {
            String errorMessage = "User with MRN ID " + mrnId + " not found.";
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        User user = userOptional.get();

        // Process the Excel file
        if (file == null || file.isEmpty()) {
            String errorMessage = "Uploaded file is empty.";
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                String errorMessage = "Excel sheet is missing or empty.";
                logger.error(errorMessage);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                // Retrieve cell values
                Integer day = getCellValueAsInteger(row.getCell(2));
                String category = getCellValueAsString(row.getCell(3));
                String exerciseName = getCellValueAsString(row.getCell(4));
                String details = getCellValueAsString(row.getCell(5));
                String status = getCellValueAsString(row.getCell(6));

                // Validate critical fields
                if (day == null || exerciseName == null) {
                    logger.warn("Missing critical data at row {}. Skipping...", row.getRowNum());
                    continue;
                }

                // Determine the chart number based on the day (divide by 15 and add 1)
                int chartNumber = (int) Math.ceil(day / 15.0);

                // Retrieve or create a new exercise chart for the user
                ExerciseChart chart = exerciseChartRepository.findByUserAndChartNumber(user, chartNumber)
                        .orElseGet(() -> {
                            ExerciseChart newChart = new ExerciseChart();
                            newChart.setUser(user);
                            newChart.setChartNumber(chartNumber);
                            return exerciseChartRepository.save(newChart);
                        });

                // Check if the exercise detail already exists
                if (exerciseDetailRepository.findByExerciseChartAndDayAndExerciseName(chart, day, exerciseName).isPresent()) {
                    logger.info("Exercise {} for day {} already exists. Skipping...", exerciseName, day);
                    continue;
                }

                // Save the exercise detail
                ExerciseDetail exerciseDetail = new ExerciseDetail();
                exerciseDetail.setExerciseChart(chart);
                exerciseDetail.setDay(day);
                exerciseDetail.setCategory(category);
                exerciseDetail.setExerciseName(exerciseName);
                exerciseDetail.setDetails(details);
                exerciseDetail.setStatus(status);

                exerciseDetailRepository.save(exerciseDetail);
            }

            String successMessage = "Exercises assigned successfully.";
            logger.info(successMessage);
            return ResponseEntity.ok(successMessage);

        } catch (Exception e) {
            String errorMessage = "Failed to process Excel file for user with MRN ID " + mrnId;
            logger.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) return null;
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.toString();
    }

    private Integer getCellValueAsInteger(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) return null;
        if (cell.getCellType() == CellType.NUMERIC) return (int) cell.getNumericCellValue();
        return null;
    }
}

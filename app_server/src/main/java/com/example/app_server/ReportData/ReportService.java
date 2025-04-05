package com.example.app_server.ReportData;

import com.example.app_server.UserAccountCreation.User;
import com.example.app_server.UserAccountCreation.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ReportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public String saveReport(String userId, MultipartFile file) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String jsonData = new String(file.getBytes(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonData);

            // 🛑 Debugging: Print the received JSON
            System.out.println("Received JSON: " + jsonData);

            // ✅ Validate "reportType" field
            if (jsonNode.get("ReportType") == null) {
                return "Error: Missing 'ReportType' in JSON!";
            }

            String type = jsonNode.get("ReportType").asText();
            ReportType reportType = ReportType.valueOf(type.toUpperCase());

            if (user.getReport() != null) {
                return "User already has a report!";
            }

            Report report = new Report();
            report.setType(reportType);
            report.setUser(user);
            report = reportRepository.save(report);

            // ✅ Validate "categories" field before iterating
            JsonNode categoriesNode = jsonNode.get("categories");
            if (categoriesNode == null || !categoriesNode.isArray()) {
                return "Error: 'categories' is missing or not an array!";
            }

            // Process categories
            for (JsonNode categoryNode : categoriesNode) {
                Category category = new Category();
                category.setType(categoryNode.get("name").asText());
                category.setReport(report);
                category = categoryRepository.save(category);

                // ✅ Validate "subcategories" before iterating
                JsonNode subcategoriesNode = categoryNode.get("subcategories");
                if (subcategoriesNode == null || !subcategoriesNode.isArray()) {
                    continue; // Skip if no subcategories exist
                }

                for (JsonNode subcategoryNode : subcategoriesNode) {
                    Subcategory subcategory = new Subcategory();
                    subcategory.setName(subcategoryNode.get("name").asText());
                    subcategory.setCategory(category);
                    subcategory = subcategoryRepository.save(subcategory);

                    // ✅ Validate "scores" field before iterating
                    JsonNode scoreNodes = subcategoryNode.get("subcategories");
                    if (scoreNodes == null || !scoreNodes.isArray()) {
                        continue;
                    }

                    for (JsonNode scoreNode : scoreNodes) {
                        Score score = new Score();
                        score.setName(scoreNode.get("name").asText());
                        score.setScore(scoreNode.get("score").asText());
                        score.setSubcategory(subcategory);
                        scoreRepository.save(score);
                    }
                }
            }
            return "Report uploaded successfully!";
        } catch (IOException e) {
            return "Error processing file: " + e.getMessage();
        }
    }


    // **2. Get Report by User ID**
    public Map<String, Object> getReportByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = reportRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        // Build a structured response with only necessary fields
        Map<String, Object> response = new HashMap<>();
        response.put("ReportType", report.getType().toString());

        List<Map<String, Object>> categoryList = new ArrayList<>();
        for (Category category : categoryRepository.findByReport(report)) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("type", category.getType());

            List<Map<String, Object>> subcategoryList = new ArrayList<>();
            for (Subcategory subcategory : subcategoryRepository.findByCategory(category)) {
                Map<String, Object> subcategoryMap = new HashMap<>();
                subcategoryMap.put("name", subcategory.getName());

                List<Map<String, Object>> scoreList = new ArrayList<>();
                for (Score score : scoreRepository.findBySubcategory(subcategory)) {
                    Map<String, Object> scoreMap = new HashMap<>();
                    scoreMap.put("name", score.getName());
                    scoreMap.put("score", score.getScore());
                    scoreList.add(scoreMap);
                }
                subcategoryMap.put("scores", scoreList);
                subcategoryList.add(subcategoryMap);
            }
            categoryMap.put("subcategories", subcategoryList);
            categoryList.add(categoryMap);
        }

        response.put("Report", categoryList);
        return response;
    }


    // **3. Get All Reports**
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // **4. Update Report**
    public String updateReport(String userId, MultipartFile file) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Report existingReport = reportRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Report not found"));

            String jsonData = new String(file.getBytes(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonData);

            existingReport.setType(ReportType.valueOf(jsonNode.get("ReportType").asText().toUpperCase()));
            reportRepository.save(existingReport);

            // Delete old categories and subcategories
            categoryRepository.deleteAllByReport(existingReport);

            // Process new data
            for (JsonNode categoryNode : jsonNode.get("Report")) {
                Category category = new Category();
                category.setType(categoryNode.get("type").asText());
                category.setReport(existingReport);
                category = categoryRepository.save(category);

                for (JsonNode subcategoryNode : categoryNode.get("subcategories")) {
                    Subcategory subcategory = new Subcategory();
                    subcategory.setName(subcategoryNode.get("name").asText());
                    subcategory.setCategory(category);
                    subcategory = subcategoryRepository.save(subcategory);

                    for (JsonNode scoreNode : subcategoryNode.get("subcategories")) {
                        Score score = new Score();
                        score.setName(scoreNode.get("name").asText());
                        score.setScore(scoreNode.get("score").asText());
                        score.setSubcategory(subcategory);
                        scoreRepository.save(score);
                    }
                }
            }
            return "Report updated successfully!";
        } catch (IOException e) {
            return "Error updating report: " + e.getMessage();
        }
    }

    // **5. Delete Report**
    @Transactional
    public String deleteReport(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = reportRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        // Delete related categories and subcategories
        List<Category> categories = categoryRepository.findByReport(report);
        for (Category category : categories) {
            List<Subcategory> subcategories = subcategoryRepository.findByCategory(category);
            for (Subcategory subcategory : subcategories) {
                scoreRepository.deleteAll(scoreRepository.findBySubcategory(subcategory));
                subcategoryRepository.delete(subcategory);
            }
            categoryRepository.delete(category);
        }

        // Finally, delete the report
        reportRepository.delete(report);

        return "Report deleted successfully!";
    }


}

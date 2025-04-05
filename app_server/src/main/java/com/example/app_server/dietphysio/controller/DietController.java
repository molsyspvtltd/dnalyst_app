package com.example.app_server.dietphysio.controller;


import com.example.app_server.dietphysio.service.DietService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/diets")
public class DietController {

    private static final Logger logger = LoggerFactory.getLogger(DietController.class);
    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDietChart(
            @RequestParam(value = "files") MultipartFile[] files, // Changed to accept an array of files
            @RequestParam(value = "mrnId", required = true) String mrnId) {
        try {
            logger.info("Received request to upload diet charts for userId: {}", mrnId);

            // Validate files
            if (files == null || files.length == 0) {
                logger.error("No files uploaded");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No files uploaded");
            }

            // Validate file types
            for (MultipartFile file : files) {
                if (!file.getOriginalFilename().endsWith(".xlsx")) {
                    logger.error("Invalid file type uploaded: {}", file.getOriginalFilename());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type. Please upload an Excel file.");
                }
            }

            // Iterate through each file
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    logger.error("One  o                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       f the files is empty");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One of the files is empty");
                }
                // Call service to upload each chart
                dietService.uploadDietChart(file, mrnId);
            }

            logger.info("Diet charts uploaded successfully for userId: {}", mrnId);
            return ResponseEntity.ok("Diet charts uploaded successfully");
        } catch (Exception e) {
            logger.error("Failed to upload diet charts for userId: {}", mrnId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload diet charts");
        }
    }
}

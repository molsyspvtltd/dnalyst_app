package com.example.app_server.dietphysio.controller;

import com.example.app_server.dietphysio.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/excel")
class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/assignExercises/{mrnId}")
    public ResponseEntity<String> assignExercisesToUser(@PathVariable String mrnId, @RequestParam("file") MultipartFile file) {
        try {
            excelService.assignExercisesToUser(mrnId, file);
            return ResponseEntity.ok("Exercises assigned successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning exercises: " + e.getMessage());
        }
    }

}
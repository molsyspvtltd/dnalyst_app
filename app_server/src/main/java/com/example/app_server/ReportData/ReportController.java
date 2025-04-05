package com.example.app_server.ReportData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // **1. Upload Report (CREATE)**
    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> uploadReport(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(reportService.saveReport(userId, file));
    }

    // **2. Get Report by User ID (READ)**
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getReportByUser(@PathVariable String userId) {
        return ResponseEntity.ok(reportService.getReportByUserId(userId));
    }


    // **3. Get All Reports**
    @GetMapping("/all")
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    // **4. Update Report**
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateReport(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(reportService.updateReport(userId, file));
    }

    // **5. Delete Report**
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteReport(@PathVariable String userId) {
        return ResponseEntity.ok(reportService.deleteReport(userId));
    }
}

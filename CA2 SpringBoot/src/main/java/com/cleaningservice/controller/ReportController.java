package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.ReportDAO;
import com.cleaningservice.model.Report;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportDAO reportDAO = new ReportDAO();

    @GetMapping
    public List<Report> getAllReports() {
        return reportDAO.getAllReports();
    }

    @GetMapping("/user/{userID}")
    public List<Report> getReportsByUser(@PathVariable int userID) {
        return reportDAO.getReportByUserId(userID);
    }

    @GetMapping("/booking/{bookingID}")
    public List<Report> getReportsByBooking(@PathVariable int bookingID) {
        return reportDAO.getReportByBookingId(bookingID);
    }



    @PostMapping("/add")
    public String addReport(@RequestBody Report report) {
        boolean success = reportDAO.addReport(report);
        return success ? "Report added successfully!" : "Failed to add report.";
    }

    @DeleteMapping("/{reportID}")
    public ResponseEntity<String> deleteReport(@PathVariable int reportID) {
        boolean success = reportDAO.deleteReport(reportID);
        return success ? ResponseEntity.ok("Report deleted successfully.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found.");
    }
}

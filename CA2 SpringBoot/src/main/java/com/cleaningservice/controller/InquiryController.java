package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.InquiryDAO;
import com.cleaningservice.model.Inquiry;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryDAO inquiryDAO = new InquiryDAO();

    @GetMapping
    public List<Inquiry> getAllInquiries() {
        return inquiryDAO.getAllInquiries();
    }

    @GetMapping("/user/{userID}")
    public List<Inquiry> getInquiriesByUser(@PathVariable int userID) {
        return inquiryDAO.getInquiryByUserId(userID);
    }

    @GetMapping("/service/{serviceID}")
    public List<Inquiry> getInquiriesByService(@PathVariable int serviceID) {
        return inquiryDAO.getInquiryByServiceId(serviceID);
    }

    @PostMapping("/add")
    public String addInquiry(@RequestBody Inquiry inquiry) {
        boolean success = inquiryDAO.addInquiry(inquiry);
        return success ? "Inquiry added successfully!" : "Failed to add inquiry.";
    }

    @PostMapping("/update/{inquiryID}")
    public String updateInquiry(@PathVariable int inquiryID, @RequestBody Inquiry inquiry) {
        boolean success = inquiryDAO.putInquiry(inquiryID, inquiry);
        return success ? "Inquiry updated successfully!" : "Failed to update inquiry.";
    }

    @DeleteMapping("/{inquiryID}")
    public ResponseEntity<String> deleteInquiry(@PathVariable int inquiryID) {
        boolean success = inquiryDAO.deleteInquiry(inquiryID);
        return success ? ResponseEntity.ok("Inquiry deleted successfully.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inquiry not found.");
    }

    @GetMapping("/search")
    public List<Inquiry> searchInquiry(@RequestParam String keyword) {
        return inquiryDAO.searchInquiry(keyword);
    }
}

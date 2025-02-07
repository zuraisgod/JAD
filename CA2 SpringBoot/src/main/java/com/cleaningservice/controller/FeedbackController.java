package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.FeedbackDAO;
import com.cleaningservice.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    @Autowired
    private FeedbackDAO feedbackDAO;

    @PostMapping("/feedback")
    public String submitFeedback(@RequestBody Feedback feedback) {
        boolean isSaved = feedbackDAO.saveFeedback(feedback);
        if (isSaved) {
            return "Feedback submitted successfully!";
        } else {
            return "Failed to submit feedback.";
        }
    }
}

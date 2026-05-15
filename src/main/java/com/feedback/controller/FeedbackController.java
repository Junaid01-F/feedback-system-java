package com.feedback.controller;

import com.feedback.entity.Feedback;
import com.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin("*")
public class FeedbackController {

    @Autowired
    private FeedbackService service;

    @PostMapping
    public Feedback submit(@RequestBody Feedback feedback) {
        return service.saveFeedback(feedback);
    }
}
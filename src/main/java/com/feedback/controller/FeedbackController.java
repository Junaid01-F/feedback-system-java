package com.feedback.controller;

import com.feedback.entity.Feedback;
import com.feedback.service.ExcelService;
import com.feedback.service.FeedbackService;
import com.feedback.service.GoogleSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        }
)
public class FeedbackController {

    @Autowired
    private FeedbackService service;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private GoogleSheetsService googleSheetsService;

    @PostMapping
    public Feedback submit(@RequestBody Feedback feedback) {

        // 1. Save in DB
        Feedback saved = service.saveFeedback(feedback);

        // 2. Save in Excel
        try {
            excelService.saveFeedbackToExcel(feedback);
        } catch (Exception e) {
            System.out.println("⚠️ Excel save failed: " + e.getMessage());
            e.printStackTrace();
        }

        // 3. Save in Google Sheets (don't break the request if this fails)
        try {
            googleSheetsService.appendData(
                    feedback.getName(),
                    feedback.getEmail(),
                    String.valueOf(feedback.getRating()),
                    feedback.getComments()
            );
        } catch (Exception e) {
            System.out.println("⚠️ Google Sheets save failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Name: " + feedback.getName());
        System.out.println("Email: " + feedback.getEmail());
        System.out.println("Rating: " + feedback.getRating());
        System.out.println("Comments: " + feedback.getComments());

        return saved;
    }
}
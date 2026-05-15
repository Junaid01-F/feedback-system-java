package com.feedback.service;

import com.feedback.entity.Feedback;
import com.feedback.repository.FeedbackRepository;
import com.feedback.util.ExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository repo;

    public Feedback saveFeedback(Feedback feedback) {

        System.out.println("👉 API HIT: Saving feedback started");

        Feedback saved = repo.save(feedback);

        System.out.println("👉 Data saved in DB");

        List<Feedback> allFeedback = repo.findAll();

        System.out.println("👉 Calling Excel exporter...");

       // ExcelExporter.export(allFeedback);

        System.out.println("👉 Excel export finished");

        return saved;
    }

}
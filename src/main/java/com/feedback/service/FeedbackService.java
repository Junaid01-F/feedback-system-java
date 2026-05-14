package com.feedback.service;

import com.feedback.entity.Feedback;
import com.feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(Feedback feedback) {

        return feedbackRepository.save(feedback);
    }
}
package com.feedback.domain.port;

import com.feedback.domain.model.Feedback;

import java.util.Optional;

public interface FeedbackRepository {
    Feedback save(Feedback feedback);
    Optional<Feedback> findById(String id);
}

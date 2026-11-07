package com.feedback.domain.model;

import com.feedback.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class Feedback {
    private final String id;
    private final String description;
    private final Integer rating;
    private final String urgency;
    private final Instant createdAt;
    private final String periodWeek;
    private final LocalDate sentAt;

    public void validate() {
        if (description == null || description.isBlank()) {
            throw new ValidationException("description", "Description is required");
        }
        if (rating == null || rating < 0 || rating > 10) {
            throw new ValidationException("rating", "Rating must be between 0 and 10");
        }
        if (urgency == null || urgency.isBlank()) {
            throw new ValidationException("urgency", "Urgency is required");
        }
    }

    public static Feedback create(String description, Integer rating) {
        return Feedback.builder()
                .id(UUID.randomUUID().toString())
                .description(description)
                .rating(rating)
                .urgency(resolveUrgency(rating))
                .createdAt(Instant.now())
                .periodWeek("W-" + Instant.now().toString())
                .sentAt(LocalDate.now())
                .build();
    }

    private static String resolveUrgency(Integer rating) {
        if (rating == null) {
            return "LOW";
        }
        if (rating <= 3) {
            return "HIGH";
        }
        if (rating <= 6) {
            return "MEDIUM";
        }
        return "LOW";
    }
}

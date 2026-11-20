package com.feedback.domain.model;

import com.feedback.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
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

    public String calculatePeriodWeek() {
        if (sentAt == null) {
            throw new IllegalArgumentException("Sent date is required");
        }
        int year = sentAt.get(IsoFields.WEEK_BASED_YEAR);
        int week = sentAt.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        return year + "-W" + String.format("%02d", week);
    }

    public static Feedback create(String description, Integer rating) {
        LocalDate sentAt = LocalDate.now();
        Feedback unsaved = Feedback.builder().sentAt(sentAt).build();
        return Feedback.builder()
                .id(UUID.randomUUID().toString())
                .description(description)
                .rating(rating)
                .urgency(resolveUrgency(rating))
                .createdAt(Instant.now())
                .periodWeek(unsaved.calculatePeriodWeek())
                .sentAt(sentAt)
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

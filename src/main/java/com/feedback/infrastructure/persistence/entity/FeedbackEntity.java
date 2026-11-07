package com.feedback.infrastructure.persistence.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Builder
public class FeedbackEntity {
    private String id;
    private String description;
    private Integer rating;
    private String urgency;
    private Instant createdAt;
    private String periodWeek;
    private LocalDate sentAt;
}

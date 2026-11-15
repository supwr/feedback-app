package com.feedback.application.dto.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@RegisterForReflection
public class FeedbackResponse {
    private String id;
    private String description;
    private Integer rating;
    private String urgency;
    private Instant createdAt;
}

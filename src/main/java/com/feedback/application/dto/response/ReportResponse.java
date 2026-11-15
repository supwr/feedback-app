package com.feedback.application.dto.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@RegisterForReflection
public class ReportResponse {
    private String status;
    private String message;
    private String queueName;
}

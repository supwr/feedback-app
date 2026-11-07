package com.feedback.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {
    private String status;
    private String message;
    private String queueName;
}

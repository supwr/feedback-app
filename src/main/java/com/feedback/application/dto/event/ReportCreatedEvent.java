package com.feedback.application.dto.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReportCreatedEvent {
    private LocalDate date;
}

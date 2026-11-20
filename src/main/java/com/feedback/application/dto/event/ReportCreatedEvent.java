package com.feedback.application.dto.event;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@RegisterForReflection
public class ReportCreatedEvent {
    private LocalDate date;
    private String periodWeek;
}

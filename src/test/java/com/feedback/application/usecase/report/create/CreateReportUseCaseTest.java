package com.feedback.application.usecase.report.create;

import com.feedback.application.dto.event.ReportCreatedEvent;
import com.feedback.application.dto.request.CreateReportRequest;
import com.feedback.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateReportUseCaseTest {

    @Test
    void publishesDateAndCalculatedPeriodWeek() {
        AtomicReference<Object> published = new AtomicReference<>();
        CreateReportUseCase useCase = new CreateReportUseCase((queueName, payload) -> published.set(payload));
        useCase.reportQueueName = "feedback-events";

        CreateReportRequest request = CreateReportRequest.builder()
                .date(LocalDate.of(2026, 11, 20))
                .build();

        useCase.execute(request);

        ReportCreatedEvent event = (ReportCreatedEvent) published.get();
        assertEquals(request.getDate(), event.getDate());
        assertEquals("2026-W47", event.getPeriodWeek());
    }

    @Test
    void rejectsMissingDate() {
        CreateReportUseCase useCase = new CreateReportUseCase((queueName, payload) -> {});

        ValidationException exception = assertThrows(ValidationException.class,
                () -> useCase.execute(CreateReportRequest.builder().build()));
        assertEquals("date", exception.getField());
    }

}

package com.feedback.application.usecase.report.create;

import com.feedback.application.dto.event.ReportCreatedEvent;
import com.feedback.application.dto.request.CreateReportRequest;
import com.feedback.application.dto.response.ReportResponse;
import com.feedback.domain.port.ReportPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class CreateReportUseCase {
    private final ReportPublisher reportPublisher;

    public ReportResponse execute(CreateReportRequest request) {
        ReportCreatedEvent event = ReportCreatedEvent.builder()
                .date(java.time.LocalDate.now())
                .build();

        reportPublisher.publish("report-queue", event);
        return ReportResponse.builder()
                .status("ACCEPTED")
                .message("Report request accepted")
                .queueName("report-queue")
                .build();
    }
}

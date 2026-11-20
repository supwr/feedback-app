package com.feedback.application.usecase.report.create;

import com.feedback.application.dto.event.ReportCreatedEvent;
import com.feedback.application.dto.request.CreateReportRequest;
import com.feedback.application.dto.response.ReportResponse;
import com.feedback.domain.exception.ValidationException;
import com.feedback.domain.model.Feedback;
import com.feedback.domain.port.ReportPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class CreateReportUseCase {
    private final ReportPublisher reportPublisher;

    @ConfigProperty(name = "feedback.report.queue.name")
    String reportQueueName;

    public ReportResponse execute(CreateReportRequest request) {
        validate(request);

        Feedback period = Feedback.builder().sentAt(request.getDate()).build();
        ReportCreatedEvent event = ReportCreatedEvent.builder()
                .date(request.getDate())
                .periodWeek(period.calculatePeriodWeek())
                .build();

        reportPublisher.publish(reportQueueName, event);
        return ReportResponse.builder()
                .status("ACCEPTED")
                .message("Report request accepted")
                .queueName(reportQueueName)
                .build();
    }

    private void validate(CreateReportRequest request) {
        if (request == null || request.getDate() == null) {
            throw new ValidationException("date", "Date is required");
        }
    }
}

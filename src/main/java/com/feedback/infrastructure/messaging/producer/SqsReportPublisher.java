package com.feedback.infrastructure.messaging.producer;

import com.feedback.domain.port.ReportPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class SqsReportPublisher implements ReportPublisher {
    private final SqsClient sqsClient;

    @Override
    public void publish(String queueName, Object payload) {
        String queueUrl = sqsClient.getQueueUrl(builder -> builder.queueName(queueName)).queueUrl();

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(String.valueOf(payload))
                .build());
    }
}

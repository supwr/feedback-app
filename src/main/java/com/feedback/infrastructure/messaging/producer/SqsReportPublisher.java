package com.feedback.infrastructure.messaging.producer;

import com.feedback.domain.port.ReportPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class SqsReportPublisher implements ReportPublisher {
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(String queueName, Object payload) {
        String queueUrl = sqsClient.getQueueUrl(builder -> builder.queueName(queueName)).queueUrl();

        try {
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(objectMapper.writeValueAsString(payload))
                    .build());
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new IllegalArgumentException("Não foi possível serializar o evento", e);
        }
    }
}

package com.feedback.domain.port;

public interface ReportPublisher {
    void publish(String queueName, Object payload);
}

package com.feedback.infrastructure.persistence.repository;

import com.feedback.domain.model.Feedback;
import com.feedback.domain.port.FeedbackRepository;
import com.feedback.infrastructure.persistence.entity.FeedbackEntity;
import com.feedback.infrastructure.persistence.mapper.FeedbackPersistenceMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class DynamoDbFeedbackRepository implements FeedbackRepository {
    private final DynamoDbClient dynamoDbClient;
    private final FeedbackPersistenceMapper feedbackPersistenceMapper;

    @ConfigProperty(name = "feedback.table.name")
    String feedbackTableName;

    @Override
    public Feedback save(Feedback feedback) {
        FeedbackEntity entity = feedbackPersistenceMapper.toEntity(feedback);
        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName(feedbackTableName)
                .item(Map.of(
                        "id", AttributeValue.fromS(entity.getId()),
                        "description", AttributeValue.fromS(entity.getDescription()),
                        "rating", AttributeValue.fromN(String.valueOf(entity.getRating())),
                        "urgency", AttributeValue.fromS(entity.getUrgency()),
                        "createdAt", AttributeValue.fromS(entity.getCreatedAt().toString()),
                        "periodWeek", AttributeValue.fromS(entity.getPeriodWeek()),
                        "sentAt", AttributeValue.fromS(entity.getSentAt().toString())
                ))
                .build());
        return feedback;
    }

    @Override
    public Optional<Feedback> findById(String id) {
        return Optional.empty();
    }
}

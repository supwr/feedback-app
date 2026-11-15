package com.feedback.infrastructure.bootstrap;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.GlobalSecondaryIndex;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.Projection;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;

@Startup
@ApplicationScoped
@IfBuildProfile("local")
public class LocalstackInitializer {

    @Inject
    DynamoDbClient dynamoDbClient;

    @Inject
    SqsClient sqsClient;

    @ConfigProperty(name = "feedback.table.name")
    String feedbackTableName;

    @ConfigProperty(name = "feedback.report.queue.name")
    String reportQueueName;

    @ConfigProperty(name = "feedback.notification.queue.name")
    String notificationQueueName;

    @PostConstruct
    void init() {
        System.out.println("Initializing LocalStack resources...");
        createFeedbackTableIfMissing();
        createQueueIfMissing(reportQueueName);
        createQueueIfMissing(notificationQueueName);
    }

    private void createFeedbackTableIfMissing() {
        try {
            dynamoDbClient.createTable(CreateTableRequest.builder()
                    .tableName(feedbackTableName)
                    .attributeDefinitions(
                            AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build(),
                            AttributeDefinition.builder().attributeName("periodWeek").attributeType(ScalarAttributeType.S).build(),
                            AttributeDefinition.builder().attributeName("createdAt").attributeType(ScalarAttributeType.S).build()
                    )
                    .keySchema(KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .globalSecondaryIndexes(GlobalSecondaryIndex.builder()
                            .indexName("periodWeek-createdAt-gsi")
                            .keySchema(
                                    KeySchemaElement.builder().attributeName("periodWeek").keyType(KeyType.HASH).build(),
                                    KeySchemaElement.builder().attributeName("createdAt").keyType(KeyType.RANGE).build()
                            )
                            .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
                            .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                            .build())
                    .build());
        } catch (ResourceInUseException ignored) {
            // table already exists
        }
    }

    private void createQueueIfMissing(String queueName) {
        try {
            sqsClient.createQueue(CreateQueueRequest.builder().queueName(queueName).build());
        } catch (Exception ignored) {
            // queue already exists or endpoint is not running in the current profile
        }
    }
}

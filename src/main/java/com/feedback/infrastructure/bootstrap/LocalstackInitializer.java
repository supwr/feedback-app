package com.feedback.infrastructure.bootstrap;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocalstackInitializer {
    // Quarkus Amazon Dev Services manages LocalStack automatically for local development.
    // This no-op bean prevents duplicate provisioning attempts and avoids conflicts with the dynamic dev-service endpoint.
}


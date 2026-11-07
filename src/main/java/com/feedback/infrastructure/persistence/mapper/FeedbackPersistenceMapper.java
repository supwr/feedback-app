package com.feedback.infrastructure.persistence.mapper;

import com.feedback.domain.model.Feedback;
import com.feedback.infrastructure.persistence.entity.FeedbackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackPersistenceMapper {
    FeedbackEntity toEntity(Feedback feedback);
    Feedback toDomain(FeedbackEntity entity);
}

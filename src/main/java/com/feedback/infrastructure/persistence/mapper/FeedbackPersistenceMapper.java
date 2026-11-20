package com.feedback.infrastructure.persistence.mapper;

import com.feedback.domain.model.Feedback;
import com.feedback.infrastructure.persistence.entity.FeedbackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;


@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackPersistenceMapper {
    @Mapping(target = "periodWeek", expression = "java(feedback.calculatePeriodWeek())")
    FeedbackEntity toEntity(Feedback feedback);
    Feedback toDomain(FeedbackEntity entity);

}

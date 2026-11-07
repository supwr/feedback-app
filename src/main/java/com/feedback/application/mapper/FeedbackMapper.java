package com.feedback.application.mapper;

import com.feedback.application.dto.response.FeedbackResponse;
import com.feedback.domain.model.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackMapper {
    FeedbackResponse toResponse(Feedback feedback);
}

package com.feedback.application.usecase.feedback.create;

import com.feedback.application.dto.request.CreateFeedbackRequest;
import com.feedback.application.dto.response.FeedbackResponse;
import com.feedback.application.mapper.FeedbackMapper;
import com.feedback.domain.model.Feedback;
import com.feedback.domain.port.FeedbackRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class CreateFeedbackUseCase {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public FeedbackResponse execute(CreateFeedbackRequest request) {
        Feedback feedback = Feedback.create(request.getDescription(), request.getRating());
        feedback.validate();
        Feedback saved = feedbackRepository.save(feedback);
        return feedbackMapper.toResponse(saved);
    }
}

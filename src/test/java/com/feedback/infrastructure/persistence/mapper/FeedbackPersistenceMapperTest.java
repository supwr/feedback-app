package com.feedback.infrastructure.persistence.mapper;

import com.feedback.domain.model.Feedback;
import com.feedback.infrastructure.persistence.entity.FeedbackEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackPersistenceMapperTest {
    private final FeedbackPersistenceMapper mapper = Mappers.getMapper(FeedbackPersistenceMapper.class);

    @Test
    void generatesIsoWeekForSentDate() {
        Feedback feedback = Feedback.builder()
                .sentAt(java.time.LocalDate.of(2026, 11, 16))
                .build();

        FeedbackEntity entity = mapper.toEntity(feedback);

        assertEquals("2026-W47", entity.getPeriodWeek());
    }

    @Test
    void generatesPreviousIsoYearAtYearBoundary() {
        Feedback start = Feedback.builder().sentAt(java.time.LocalDate.of(2026, 1, 1)).build();
        Feedback end = Feedback.builder().sentAt(java.time.LocalDate.of(2026, 12, 31)).build();
        assertEquals("2026-W01", start.calculatePeriodWeek());
        assertEquals("2026-W53", end.calculatePeriodWeek());
    }
}

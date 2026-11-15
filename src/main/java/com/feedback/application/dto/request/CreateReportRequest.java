package com.feedback.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateReportRequest {
}

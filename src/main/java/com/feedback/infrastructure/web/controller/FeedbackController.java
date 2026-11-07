package com.feedback.infrastructure.web.controller;

import com.feedback.application.dto.request.CreateFeedbackRequest;
import com.feedback.application.dto.response.FeedbackResponse;
import com.feedback.application.usecase.feedback.create.CreateFeedbackUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class FeedbackController {

    @Inject
    CreateFeedbackUseCase createFeedbackUseCase;

    @POST
    @Path("/feedbacks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFeedback(CreateFeedbackRequest request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Request body is required")
                    .build();
        }

        FeedbackResponse response = createFeedbackUseCase.execute(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}

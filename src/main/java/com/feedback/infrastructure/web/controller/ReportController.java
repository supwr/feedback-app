package com.feedback.infrastructure.web.controller;

import com.feedback.application.dto.request.CreateReportRequest;
import com.feedback.application.dto.response.ReportResponse;
import com.feedback.application.usecase.report.create.CreateReportUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class ReportController {

    @Inject
    CreateReportUseCase createReportUseCase;

    @POST
    @Path("/reports")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReport(CreateReportRequest request) {
        ReportResponse response = createReportUseCase.execute(request);
        return Response.accepted(response).build();
    }
}

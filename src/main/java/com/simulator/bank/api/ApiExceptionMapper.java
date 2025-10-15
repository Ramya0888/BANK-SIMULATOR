package com.simulator.bank.api;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception ex) {
        ex.printStackTrace(); 
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorPayload("Internal error", ex.getMessage()))
                .build();
    }

    public static class ErrorPayload {
        public String error;
        public String details;
        public ErrorPayload() {}
        public ErrorPayload(String error, String details) {
            this.error = error; this.details = details;
        }
    }
}

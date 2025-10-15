package com.simulator.bank.api;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        int status = 500;
        if (ex instanceof IllegalArgumentException) status = 400;
    

        String msg = ex.getMessage() != null ? ex.getMessage() : ex.toString();
        String safe = msg.replace("\"", "\\\"");

        String json = "{\"error\":\"" + safe + "\"}";
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(json)
                .build();
    }
}

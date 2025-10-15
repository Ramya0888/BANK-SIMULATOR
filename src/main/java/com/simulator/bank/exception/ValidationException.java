package com.simulator.bank.exception;

import jakarta.ws.rs.BadRequestException;

public class ValidationException extends BadRequestException {
    public ValidationException(String message) {
        super(message);
    }
}

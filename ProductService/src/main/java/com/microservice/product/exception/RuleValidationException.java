package com.microservice.product.exception;

public class RuleValidationException extends RuntimeException {

    public RuleValidationException(String message) {
        super(message);
    }
}
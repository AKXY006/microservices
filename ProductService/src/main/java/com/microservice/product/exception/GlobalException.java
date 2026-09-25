package com.microservice.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservice.product.util.ResponseStructure;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseStructure<String> resourceNotFoundException(ResourcesNotFoundException exception) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Resource Not Found");
        response.setData(exception.getMessage());
        return response;
    }

    @ExceptionHandler(RecordAlreadyExistException.class)
    public ResponseStructure<String> recordAlreadyExistException(RecordAlreadyExistException exception) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CONFLICT.value());
        response.setMessage("Record Already Exists");
        response.setData(exception.getMessage());
        return response;
    }

    @ExceptionHandler(RuleValidationException.class)
    public ResponseStructure<String> ruleValidationException(RuleValidationException exception) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Rule Validation Failed");
        response.setData(exception.getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseStructure<String> globalException(Exception exception) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Something went wrong");
        response.setData(exception.getMessage());
        return response;
    }
}
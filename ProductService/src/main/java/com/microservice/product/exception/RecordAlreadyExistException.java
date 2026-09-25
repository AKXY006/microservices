package com.microservice.product.exception;

public class RecordAlreadyExistException extends RuntimeException {
	
	public RecordAlreadyExistException(String message) {
		super(message);
	}

}

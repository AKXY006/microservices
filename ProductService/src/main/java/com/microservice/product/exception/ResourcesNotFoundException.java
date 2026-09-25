package com.microservice.product.exception;

public class ResourcesNotFoundException extends RuntimeException{
	
	public ResourcesNotFoundException(String message) {
		super(message);
	}

}

package exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import util.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleResourceNotFoundException(ResourceNotFoundException exception){
    	
    	ResponseStructure<String> responseStructure = new ResponseStructure<String>();
    	responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
    	responseStructure.setMessage("Resource Not Found");
    	responseStructure.setData(exception.getMessage());
    	
    	return new ResponseEntity<>(responseStructure,HttpStatus.NOT_FOUND);
    	
    } 
    
    @ExceptionHandler(RecordAlreadyExistException.class)
    public ResponseEntity<ResponseStructure<String>> handleRecordAlreadyExistException(RecordAlreadyExistException exception) {

        ResponseStructure<String> responseStructure = new ResponseStructure<String>();

        responseStructure.setStatusCode(HttpStatus.CONFLICT.value());
        responseStructure.setMessage("Resource Already Exists");
        responseStructure.setData(exception.getMessage());

        return new ResponseEntity<>(responseStructure,HttpStatus.CONFLICT);
    }
    
    
    @ExceptionHandler(RuleValidationException.class)
    public ResponseEntity<ResponseStructure<String>> handleRuleValidationException(RuleValidationException exception) {

        ResponseStructure<String> responseStructure = new ResponseStructure<String>();

        responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
        responseStructure.setMessage("Rule Validation Failed");
        responseStructure.setData(exception.getMessage());

        return new ResponseEntity<>(responseStructure,HttpStatus.BAD_REQUEST);
    }
}
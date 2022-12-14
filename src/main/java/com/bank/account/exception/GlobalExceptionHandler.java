package com.bank.account.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
	        ExceptionResponse response = new ExceptionResponse();
	        response.setErrorCode("NOT_FOUND");
	        response.setErrorMessage(ex.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        response.setErrorType(ex.getMessage().contains("Debit")?"Debit":"Credit");

	        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(CustomException.class)
	    public ResponseEntity<ExceptionResponse> customException(CustomException ex) {
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("BAD_REQUEST");
	        response.setErrorMessage(ex.getMessage());
	        response.setTimestamp(LocalDateTime.now());

	        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	    }

}

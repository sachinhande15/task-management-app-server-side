package com.learningwithsachin.taskmanagement.exception;

import com.learningwithsachin.taskmanagement.utilities.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions (MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseHandler.generateResponse("Validation Error", HttpStatus.BAD_REQUEST, errors);
	}

	// Handle specific exceptions, e.g., ResourceNotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException (ResourceNotFoundException ex, WebRequest request) {
		return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
	}

	// Handle generic exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGlobalException (Exception ex, WebRequest request) {
		return ResponseHandler.generateResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}
	// Handle User Not found exceptions
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException (UserNotFoundException ex, WebRequest request) {
		return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> handleUserAlreadyPresentException (UserAlreadyExistsException ex, WebRequest request) {
		return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.OK, null);
	}

	// Handle invalid URLs
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("message", "The requested URL was not found on this server");
		errorDetails.put("path", ex.getRequestURL());
		errorDetails.put("error", "Not Found");

		return ResponseHandler.generateResponse("Invalid URL", HttpStatus.NOT_FOUND, errorDetails);
	}
}

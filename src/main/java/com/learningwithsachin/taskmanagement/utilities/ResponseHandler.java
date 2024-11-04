package com.learningwithsachin.taskmanagement.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {

	public static ResponseEntity<Object> generateResponse (String message, HttpStatus status, Object data) {
		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", getDataTime());
		response.put("status", status.value());
		response.put("message", message);
		response.put("data", data);
		return new ResponseEntity<>(response, status);
	}

	public static String getDataTime(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.now().format(formatter);
	}
}

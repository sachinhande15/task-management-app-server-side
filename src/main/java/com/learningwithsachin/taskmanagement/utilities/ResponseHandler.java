package com.learningwithsachin.taskmanagement.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CACHE_CONTROL, "max-age=700");
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", getDataTime());
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, headers, status);
    }

    public static String getDataTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public static void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("timestamp", getDataTime());
            responseBody.put("status", status.value());
            responseBody.put("error", status.getReasonPhrase());
            responseBody.put("message", message);

            // Convert Map to JSON string (use ObjectMapper or a similar library)
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(responseBody);
            response.getWriter().write(json);
        } catch (IOException e) {
            logger.error("Error writing error response: {}", e.getMessage());
        }
    }
}

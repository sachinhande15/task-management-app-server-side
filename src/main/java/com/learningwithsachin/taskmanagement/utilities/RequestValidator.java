package com.learningwithsachin.taskmanagement.utilities;


import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestValidator {

    public Map<String, String> validate(BindingResult bindingResult, Long queryParameter) {

        if (queryParameter != null) {
            validate(queryParameter);
        }
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    public Map<String, String> validate(Long queryParameter) {
        Map<String, String> errors = new HashMap<>();
        if (queryParameter == null) {
            errors.put("id", "Path variable 'id' is required and cannot be null");
        } else if (queryParameter <= 0) {
            errors.put("id", "Path variable 'id' must be a positive number.");
        }
        return errors;
    }
}

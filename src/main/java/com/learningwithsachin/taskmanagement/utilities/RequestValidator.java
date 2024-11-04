package com.learningwithsachin.taskmanagement.utilities;


import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestValidator {

	public Map<String, String> validate(BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : bindingResult.getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return errors;
	}
}

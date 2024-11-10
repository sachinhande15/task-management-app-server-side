package com.learningwithsachin.taskmanagement.controller;


import com.learningwithsachin.taskmanagement.dto.AuthRequest;
import com.learningwithsachin.taskmanagement.model.User;
import com.learningwithsachin.taskmanagement.services.AuthService;
import com.learningwithsachin.taskmanagement.utilities.JwtUtil;
import com.learningwithsachin.taskmanagement.utilities.RequestValidator;
import com.learningwithsachin.taskmanagement.utilities.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    private final RequestValidator requestValidator;


    public AuthController(RequestValidator requestValidator, AuthService authService, JwtUtil jwtUtil) {
        this.requestValidator = requestValidator;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@Valid @RequestBody User user, BindingResult result) {

        Map<String, String> errors = requestValidator.validate(result, null);
        if (!errors.isEmpty()) {
            return ResponseHandler.generateResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
        }
        boolean userSaved = authService.register(user);
        if (userSaved) {

            return ResponseHandler.generateResponse("User registered ", HttpStatus.CREATED, true);
        }
        logger.warn("User registration failed: User could not be saved.");
        return ResponseHandler.generateResponse("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR, false);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@Valid @RequestBody AuthRequest authRequest, BindingResult result) {
        Map<String, String> errors = requestValidator.validate(result, null);
        if (!errors.isEmpty()) {
            return ResponseHandler.generateResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
        }
        String jwtToken = authService.login(authRequest).getJwt();
        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("Bearer", jwtToken);

        return ResponseHandler.generateResponse("Login successful", HttpStatus.OK, tokenData);
    }
}

package com.learningwithsachin.taskmanagement.controller;


import com.learningwithsachin.taskmanagement.model.User;
import com.learningwithsachin.taskmanagement.services.UserService;
import com.learningwithsachin.taskmanagement.utilities.RequestValidator;
import com.learningwithsachin.taskmanagement.utilities.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;


	private final RequestValidator requestValidator;


	@Autowired
	public UserController (UserService userService, RequestValidator requestValidator) {
		this.userService = userService;
		this.requestValidator = requestValidator;
	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers () {
		List<User> userList = userService.getUsers();
		if ( userList.isEmpty() ) {
			logger.warn("No user found");
			return ResponseHandler.generateResponse("No users found", HttpStatus.NOT_FOUND, null);
		}
		logger.info("getting users...");
		return ResponseHandler.generateResponse("users list ", HttpStatus.OK, userList);
	}

	@PostMapping("/register")
	public ResponseEntity<?> addUser (@Valid @RequestBody User user, BindingResult result) {

		Map<String, String> errors = requestValidator.validate(result);
		if ( !errors.isEmpty() ) {
			return ResponseHandler.generateResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
		}
		boolean userSaved = userService.saveUser(user);
		if ( userSaved ) {

			return ResponseHandler.generateResponse("User registered ", HttpStatus.CREATED, true);
		}
		logger.warn("User registration failed: User could not be saved.");
		return ResponseHandler.generateResponse("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR, false);
	}

}

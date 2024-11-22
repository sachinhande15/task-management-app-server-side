package com.learningwithsachin.taskmanagement.controller;


import com.learningwithsachin.taskmanagement.dto.UserUpdateDTO;
import com.learningwithsachin.taskmanagement.model.User;
import com.learningwithsachin.taskmanagement.repository.UserRepo;
import com.learningwithsachin.taskmanagement.services.CloudinaryService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	private final RequestValidator requestValidator;

	private final UserRepo userRepo;

	@Autowired
	private CloudinaryService cloudinaryService;

	@Autowired
	public UserController (UserService userService, RequestValidator requestValidator, UserRepo userRepo) {
		this.userService = userService;
		this.requestValidator = requestValidator;
		this.userRepo = userRepo;
	}

	@GetMapping("/users")
	public ResponseEntity<Object> fetchUsers () {
		List<User> userList = userService.getUsers();
		if ( userList.isEmpty() ) {
			logger.warn("No user found");
			return ResponseHandler.generateResponse("No users found", HttpStatus.NOT_FOUND, null);
		}
		Map<String, Object> usersMap = new HashMap<>(userList.size());
		usersMap.put("users", userList);
		logger.info("getting users...");
		return ResponseHandler.generateResponse("users list ", HttpStatus.OK, usersMap);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Object> deleteUser (@PathVariable Long id) {
		Map<String, String> errors = requestValidator.validate(id);
		if ( !errors.isEmpty() ) {
			return ResponseHandler.generateResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
		}
		userService.deleteUserById(id);
		return ResponseHandler.generateResponse("User deleted successfully", HttpStatus.OK, null);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<Object> updateUser (@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO, BindingResult result) {
		Map<String, String> errors = requestValidator.validate(result, id);
		if ( !errors.isEmpty() ) {
			return ResponseHandler.generateResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
		}
		boolean updateSuccess = userService.updateUserById(id, userUpdateDTO);
		if ( updateSuccess ) {
			return ResponseHandler.generateResponse("User updated successfully", HttpStatus.OK, true);
		}
		return ResponseHandler.generateResponse("User not found", HttpStatus.NOT_FOUND, false);
	}

	@PutMapping("/updatePassword")
	public ResponseEntity<Object> updateUserPassword (@RequestBody Map<String, String> request) {

		String emailId = request.get("emailId");
		String newPassword = request.get("newPassword");

		// Check if emailId and newPassword are not null or empty
		if ( emailId == null || emailId.isEmpty() ) {
			return ResponseHandler.generateResponse("Email ID is required", HttpStatus.BAD_REQUEST, null);
		}
		if ( newPassword == null || newPassword.isEmpty() ) {
			return ResponseHandler.generateResponse("New password is required", HttpStatus.BAD_REQUEST, null);
		}
		return ResponseHandler.generateResponse("Password updated successful", HttpStatus.OK, userService.updatePassword(emailId, newPassword));
	}

	@PostMapping("/uploadProfilePicture")
	public ResponseEntity<?> uploadProfilePicture (@RequestParam("file") MultipartFile file, Principal principal) throws IOException {

		String username = principal.getName();
		User user = userRepo.findByusername(username).orElseThrow(() -> new RuntimeException("User Not Found"));

		// Upload to Cloudinary
		String imageUrl = cloudinaryService.uploadFile(file);

		// Save file URL to user profile
		user.setProfileImage(imageUrl);
		userRepo.save(user);

		return ResponseHandler.generateResponse("Profile image uploaded successfully", HttpStatus.OK, imageUrl);
	}

}

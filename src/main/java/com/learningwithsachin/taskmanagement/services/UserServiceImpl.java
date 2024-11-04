package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.exception.UserAlreadyExistsException;
import com.learningwithsachin.taskmanagement.model.User;
import com.learningwithsachin.taskmanagement.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	 public UserServiceImpl (UserRepo userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean saveUser (User user) {
		User foundUser = userRepo.findByusername(user.getUsername());
		if ( foundUser == null ) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userRepo.save(user);
			logger.info("user saved in the database successfully");
			return true;
		}
		throw new UserAlreadyExistsException("User with username "+ user.getUsername() +" "+"already exists.");
	}

	@Override
	public List<User> getUsers () {
		return userRepo.findAll();
	}

	@Override
	public User getUserById (String id) {
		return null;
	}

	@Override
	public boolean deleteUserById (String id) {
		return false;
	}

	@Override
	public boolean updateUserById (String id) {
		return false;
	}

	@Override
	public boolean authenticateUser (User user) {
		User foundUser = userRepo.findByusername(user.getUsername());
		if ( foundUser == null ) return false;
		return verifyPassword(user.getPassword(), foundUser.getPassword());
	}

	// Method to verify password during login
	public boolean verifyPassword (String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}

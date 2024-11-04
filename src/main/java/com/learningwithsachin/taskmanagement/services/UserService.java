package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.model.User;

import java.util.List;

public interface UserService {

	boolean saveUser(User user);

	List<User> getUsers();

	User getUserById(String id);

	boolean deleteUserById(String id);

	boolean updateUserById(String id);

	boolean authenticateUser(User user);
}

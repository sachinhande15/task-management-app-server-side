package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.dto.UserUpdateDTO;
import com.learningwithsachin.taskmanagement.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUserById(Long id);

    boolean deleteUserById(Long id);

    boolean updateUserById(Long id, UserUpdateDTO userUpdateDTO);

    boolean updatePassword(String emailId, String password);
}

package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.dto.UserUpdateDTO;
import com.learningwithsachin.taskmanagement.exception.UserNotFoundException;
import com.learningwithsachin.taskmanagement.model.User;
import com.learningwithsachin.taskmanagement.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Cacheable("users")
    @Override
    public List<User> getUsers() {
        logger.info("Getting users from the database");
        return userRepo.findAll().stream().map(
                user -> {
                    User user1 = new User();
                    user1.setUsername(user.getUsername());
                    user1.setId(user.getId());
                    return user1;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return getUserWithId(id);
    }

    private User getUserWithId(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found with ID :" + id)
        );
        return user;
    }

    @Override
    @CacheEvict
    public boolean deleteUserById(Long id) {
        User user = getUserWithId(id);
        userRepo.deleteById(user.getId());
        logger.info("user deleted successfully");
        return true;
    }

    @Override
    public boolean updateUserById(Long id, UserUpdateDTO userUpdateDTO) {

        Optional<User> existingUserOpt = userRepo.findById(id);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (userUpdateDTO.getFirstName() != null) {
                existingUser.setFirstName(userUpdateDTO.getFirstName());
            }
            if (userUpdateDTO.getLastName() != null) {
                existingUser.setLastName(userUpdateDTO.getLastName());
            }
            if (userUpdateDTO.getEmailId() != null) {
                existingUser.setEmailId(userUpdateDTO.getEmailId());
            }
            userRepo.save(existingUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(String emailId, String password) {
        User user = userRepo.findByEmailId(emailId).orElseThrow(() ->
                new UsernameNotFoundException("No user is associated with emailId : " + emailId));
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        logger.info("Password is updated successfully");
        return true;
    }
}

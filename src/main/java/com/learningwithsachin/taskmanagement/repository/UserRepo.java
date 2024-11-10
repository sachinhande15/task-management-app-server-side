package com.learningwithsachin.taskmanagement.repository;

import com.learningwithsachin.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByusername(String username);

    Optional<User> findByEmailId(String emailId);
}

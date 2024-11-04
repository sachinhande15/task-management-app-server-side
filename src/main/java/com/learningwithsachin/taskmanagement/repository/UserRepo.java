package com.learningwithsachin.taskmanagement.repository;

import com.learningwithsachin.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByusername(String username);
}

package com.learningwithsachin.taskmanagement.repository;

import com.learningwithsachin.taskmanagement.model.Task;
import com.learningwithsachin.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    Optional<Task> findByUserAndId(User user, Long taskId);
}

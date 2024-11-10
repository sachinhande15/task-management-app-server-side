package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.dto.TaskDTO;
import com.learningwithsachin.taskmanagement.exception.ApplicationException;
import com.learningwithsachin.taskmanagement.exception.ResourceNotFoundException;
import com.learningwithsachin.taskmanagement.model.Task;
import com.learningwithsachin.taskmanagement.model.User;
import com.learningwithsachin.taskmanagement.repository.TaskRepo;
import com.learningwithsachin.taskmanagement.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    TaskRepo taskRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthService authService;

    @Override
    public TaskDTO saveTask(Task task) {
        try {
            User user = authService.getAuthenticatedUser();
            task.setUser(user);
            return TaskDTO.fromTask(taskRepo.save(task));
        } catch (Exception e) {
            logger.error("Error occurred while saving task");
            throw new ApplicationException("Error occurred while saving task" + e);
        }
    }

    @Override
    public List<TaskDTO> getTasks() {
        try {
            User user = authService.getAuthenticatedUser();
            List<Task> tasks = taskRepo.findByUser(user);
            logger.info("getting tasks for user");
            return tasks.stream()
                    .map(TaskDTO::fromTask)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Error occurred while retrieving tasks for user", e);
            throw new ApplicationException("Error occurred while retrieving tasks for user" + e);
        }
    }

    @Override
    public TaskDTO getTaskById(Long taskId) {
        try {
            User user = authService.getAuthenticatedUser();
            // Find the task by user and task ID
            Task task = taskRepo.findByUserAndId(user, taskId)
                    .orElseThrow(() -> new ApplicationException("Task not found for user"));

            logger.info("getting task for user");
            return TaskDTO.fromTask(task);

        } catch (Exception e) {
            logger.error("Error occurred while retrieving task for user", e);
            throw new ApplicationException("Error occurred while retrieving task for user" + e);
        }
    }

    @Override
    public boolean deleteTaskById(Long taskId) {
        // Get the authenticated user
        User user = authService.getAuthenticatedUser();

        // Find the task by user and task ID
        Task task = taskRepo.findByUserAndId(user, taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for user with taskId :" + taskId));

        // Delete the task
        taskRepo.delete(task);
        return true;
    }

    @Override
    @Transactional
    public TaskDTO updateTaskById(Long taskId, TaskDTO taskDTO) {
        try {
            // Get the authenticated user
            User user = authService.getAuthenticatedUser();

            // Find the task by user and task ID
            Task task = taskRepo.findByUserAndId(user, taskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found for user"));

           if (taskDTO.getTitle() !=null){
               task.setTitle(taskDTO.getTitle());
           }
            if (taskDTO.getDescription() !=null){
                task.setDescription(taskDTO.getTitle());
            }
            if (taskDTO.getStatus() !=null){
                task.setStatus(taskDTO.getStatus());
            }
            if (taskDTO.getDueDate() !=null){
                task.setDueDate(taskDTO.getDueDate());
            }
            // Save the updated task
            taskRepo.save(task);

            // Convert task to TaskDTO and return
            return TaskDTO.fromTask(task);
        } catch (Exception e) {
            logger.error("Error occurred while updating task", e);
            throw new ApplicationException("Error occurred while updating task: " + e.getMessage());
        }
    }
}

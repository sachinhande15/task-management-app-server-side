package com.learningwithsachin.taskmanagement.controller;


import com.learningwithsachin.taskmanagement.dto.TaskDTO;
import com.learningwithsachin.taskmanagement.model.Task;
import com.learningwithsachin.taskmanagement.services.TaskService;
import com.learningwithsachin.taskmanagement.utilities.RequestValidator;
import com.learningwithsachin.taskmanagement.utilities.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    TaskService taskService;


    @PostMapping("/tasks")
    public ResponseEntity<Object> createTask(@Valid @RequestBody Task task, BindingResult result) {

        Map<String, String> errors = requestValidator.validate(result, null);
        if (!errors.isEmpty()) {
            return ResponseHandler.generateResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
        }
        TaskDTO savedTask = taskService.saveTask(task);
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("task", savedTask);
        return ResponseHandler.generateResponse("task created successfully", HttpStatus.OK,
                taskMap);
    }

    @GetMapping("/tasks")
    public ResponseEntity<Object> getAllTasksForUser() {
        List<TaskDTO> taskDTOList = taskService.getTasks();
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("tasks", taskDTOList);
        return ResponseHandler.generateResponse("tasks fetched successfully", HttpStatus.OK,
                taskMap);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Object> getTaskWithIdForUser(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("tasks", taskDTO);
        return ResponseHandler.generateResponse("task fetched successfully with id : " +id, HttpStatus.OK,
                taskMap);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        return ResponseHandler.generateResponse("task deleted with id " + id + " successfully", HttpStatus.OK,
                taskService.deleteTaskById(id));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        TaskDTO updateTask = taskService.updateTaskById(id, taskDTO);
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("tasks", updateTask);
        return ResponseHandler.generateResponse("task fetched successfully", HttpStatus.OK,
                taskMap);
    }
}

package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.dto.TaskDTO;
import com.learningwithsachin.taskmanagement.model.Task;

import java.util.List;

public interface TaskService {

    TaskDTO saveTask(Task task);

    List<TaskDTO> getTasks();

    TaskDTO getTaskById(Long id);

    boolean deleteTaskById(Long id);

    TaskDTO updateTaskById(Long id, TaskDTO taskDTO);
}

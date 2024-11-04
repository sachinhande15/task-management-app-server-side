package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.model.Task;

import java.util.List;

public interface TaskService {

	boolean saveTask(Task task);

	List<Task> getTasks();

	Task getTaskById(String id);

	boolean deleteTaskById(String id);

	boolean updateTaskById(String id);
}

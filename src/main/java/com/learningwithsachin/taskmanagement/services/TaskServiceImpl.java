package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.model.Task;

import java.util.List;

public class TaskServiceImpl implements TaskService{
	@Override
	public boolean saveTask (Task task) {
		return false;
	}

	@Override
	public List<Task> getTasks () {
		return List.of();
	}

	@Override
	public Task getTaskById (String id) {
		return null;
	}

	@Override
	public boolean deleteTaskById (String id) {
		return false;
	}

	@Override
	public boolean updateTaskById (String id) {
		return false;
	}
}

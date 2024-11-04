package com.learningwithsachin.taskmanagement.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Title is required")
	private String title;

	@NotEmpty(message = "Description is required")
	private String description;

	@NotEmpty(message = "Due date is required")
	private LocalDateTime dueDate;
	private boolean completed;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public @NotEmpty(message = "Title is required") String getTitle () {
		return title;
	}

	public @NotEmpty(message = "Description is required") String getDescription () {
		return description;
	}

	public @NotEmpty(message = "Due date is required") LocalDateTime getDueDate () {
		return dueDate;
	}

	public boolean isCompleted () {
		return completed;
	}

	public User getUser () {
		return user;
	}

	public void setTitle (@NotEmpty(message = "Title is required") String title) {
		this.title = title;
	}

	public void setDescription (@NotEmpty(message = "Description is required") String description) {
		this.description = description;
	}

	public void setDueDate (@NotEmpty(message = "Due date is required") LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public void setCompleted (boolean completed) {
		this.completed = completed;
	}

	public void setUser (User user) {
		this.user = user;
	}
}

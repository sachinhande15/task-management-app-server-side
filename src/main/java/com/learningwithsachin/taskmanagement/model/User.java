package com.learningwithsachin.taskmanagement.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "username is required")
	private String username;
	@NotEmpty(message = "password is required")
	private String password;

	public void setUsername (@NotEmpty(message = "username is required") String username) {
		this.username = username;
	}

	public void setPassword (@NotEmpty(message = "password is required") String password) {
		this.password = password;
	}

	public @NotEmpty(message = "username is required") String getUsername () {
		return username;
	}

	public @NotEmpty(message = "password is required") String getPassword () {
		return password;
	}
}

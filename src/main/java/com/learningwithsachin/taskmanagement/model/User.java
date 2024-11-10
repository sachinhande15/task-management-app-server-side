package com.learningwithsachin.taskmanagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learningwithsachin.taskmanagement.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "username is required")
    @Column(name = "user_username")
    private String username;

    @NotEmpty(message = "password is required")
    @Column(name = "user_password")
    private String password;

    @NotEmpty(message = "First Name is required")
    @Column(name = "user_first_Name")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    @Column(name = "user_last_Name")
    private String lastName;

    @NotEmpty(message = "Email Id is required")
    @Column(name = "user_email", nullable = false, unique = true)
    private String emailId;

    @Column(name = "created_at")
    private final LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> tasks = new HashSet<>();

    public User() {
        this.createdAt = LocalDateTime.now();
        this.role = Role.USER;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}

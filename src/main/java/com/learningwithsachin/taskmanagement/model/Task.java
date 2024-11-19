package com.learningwithsachin.taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learningwithsachin.taskmanagement.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

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
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Description is required")
    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;


    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public @NotEmpty(message = "Title is required") String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Title is required") String title) {
        this.title = title;
    }

    public @NotEmpty(message = "Description is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Description is required") String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NotEmpty(message = "Due date is required") LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

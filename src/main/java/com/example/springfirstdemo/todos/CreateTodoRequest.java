package com.example.springfirstdemo.todos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CreateTodoRequest {
    @NotBlank
    private String task;

    private String description;

    private OffsetDateTime dueDate;

    @NotNull
    private UUID userId;

    public String getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "CreateTodoRequest{" +
                "task='" + task + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", userId=" + userId +
                '}';
    }
}

package com.example.springfirstdemo.user;

import com.example.springfirstdemo.todoist.UserTodoistInfo;
import com.example.springfirstdemo.todos.Todo;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users", schema = "iam")
public class AppUser {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private LocalDate dateOfBirth;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Todo> todos;

    @OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    private UserTodoistInfo todoistInfo;

    public AppUser() {
    }

    public AppUser(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserTodoistInfo getTodoistInfo() {
        return todoistInfo;
    }

    public void setTodoistInfo(UserTodoistInfo todoistInfo) {
        this.todoistInfo = todoistInfo;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                ", todoistInfo=" + todoistInfo +
                '}';
    }
}


// TODO: add UserLoginInfo
// LastLoginAt
// LastLoginIp
// DailyLoginCount

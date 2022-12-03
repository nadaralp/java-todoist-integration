package com.example.springfirstdemo.todos;

import com.example.springfirstdemo.user.AppUser;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.*;

@Entity
//@Table()
public class Todo {
    @Id
    @SequenceGenerator(
            name = "todo_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todo_sequence"
    )
    private Long id;

    @Column
    private String task;

    @Column
    private String description;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "due_date", columnDefinition = "timestamp with time zone")
    private OffsetDateTime dueDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp with time zone")
    private ZonedDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;

    public Todo() {
    }

    public Todo(String task, String description, boolean isCompleted, OffsetDateTime dueDate, AppUser user) {
        this.task = task;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public AppUser getUser() {
        return user;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setDueDate(OffsetDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                ", dueDate=" + dueDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userId=" + user.getId() +
                '}';
    }
}

// Local... types purposely have no concept of time zone. So they don't represent a moment on the timeline.
// A LocalDateTime represents a vague range of possible moments but has no real meaning until assigning an offset or time zone.
//
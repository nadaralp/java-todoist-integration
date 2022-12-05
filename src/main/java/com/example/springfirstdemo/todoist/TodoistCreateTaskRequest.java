package com.example.springfirstdemo.todoist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;

public class TodoistCreateTaskRequest {
    private String content;
    private String description;

    @JsonProperty("due_date")
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

//    private String projectId;

    public TodoistCreateTaskRequest() {
    }

    public TodoistCreateTaskRequest(String content) {
        this.content = content;
    }

    public TodoistCreateTaskRequest(String content, String description, LocalDate dueDate) {
        this.content = content;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }



    @Override
    public String toString() {
        return "TodoistCreateTaskRequest{" +
                "content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}

package com.example.springfirstdemo.todoist;

/**
 * Todoist integration client.
 * This client communicates with the todoist API and performs operations to integrate with it.
 */
public interface TodoistClient {
    TodoistCreateTaskResponse addTodoistTask(TodoistCreateTaskRequest request, String bearerAuthToken);
}
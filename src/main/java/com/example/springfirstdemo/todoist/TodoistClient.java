package com.example.springfirstdemo.todoist;

/**
 * Todoist integration client.
 * This client communicates with the todoist API and performs operations to integrate with it.
 */
public interface TodoistClient {
    TodoistCreateTaskResponse addTask(TodoistCreateTaskRequest request, String bearerAuthToken);

    void closeTask(String taskId, String bearerAuthToken);

}
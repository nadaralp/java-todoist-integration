package com.example.springfirstdemo.todoist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Example response:
 * <code>
     {
     "creator_id": "2671355",
     "created_at": "2019-12-11T22:36:50.000000Z",
     "assignee_id": null,
     "assigner_id": null,
     "comment_count": 0,
     "is_completed": false,
     "content": "Buy Milk",
     "description": "",
     "due": {
     "date": "2016-09-01",
     "is_recurring": false,
     "datetime": "2016-09-01T12:00:00.000000Z",
     "string": "tomorrow at 12",
     "timezone": "Europe/Moscow"
     },
     "id": "2995104339",
     "labels": [],
     "order": 1,
     "priority": 4,
     "project_id": "2203306141",
     "section_id": null,
     "parent_id": null,
     "url": "https://todoist.com/showTask?id=2995104339"
     }
 *
 * </code>
 */
public class TodoistCreateTaskResponse {
    private String id;
    private String content;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TodoistCreateTaskResponse{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

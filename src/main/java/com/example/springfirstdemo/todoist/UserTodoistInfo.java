package com.example.springfirstdemo.todoist;

import com.example.springfirstdemo.user.AppUser;
import jakarta.persistence.*;

@Entity
public class UserTodoistInfo {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String todoistApiKey; // must be encrypted while saving.
    @OneToOne
    private AppUser user;

    public UserTodoistInfo() {
    }

    public UserTodoistInfo(String todoistApiKey) {
        this.todoistApiKey = todoistApiKey;
    }

    public Long getId() {
        return id;
    }

    public String getTodoistApiKey() {
        return todoistApiKey;
    }

    public void setTodoistApiKey(String todoistApiKey) {
        this.todoistApiKey = todoistApiKey;
    }

    public AppUser getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserTodoistInfo{" +
                "id=" + id +
                ", todoistApiKey='" + todoistApiKey + '\'' +
                ", user=" + user +
                '}';
    }
}

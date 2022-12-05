package com.example.springfirstdemo.todoist;

import com.example.springfirstdemo.user.api_models.AppUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TodoistClientImplTest {
    TodoistClientImpl sut;

    @BeforeEach
    void setUp() {
        sut = new TodoistClientImpl();

    }

    @Test
    void addTodoistTask_ShouldAddTaskOnTodoistApp_WhenRequestSent() throws Exception {
        // Arrange
//        TodoistCreateTaskRequest request, String bearerAuthToken
        TodoistCreateTaskRequest todoistCreateTaskRequest = new TodoistCreateTaskRequest(
                "test from unit test",
                "some test description",
                LocalDate.now().plusDays(2)
        );

        String bearerToken = "5e4154c427a7ce6925160c1fad7415c95f050fc9";

        // Act
        TodoistCreateTaskResponse todoistCreateTaskResponse = sut.addTodoistTask(todoistCreateTaskRequest, bearerToken);

        // Assert
        assertNotNull(todoistCreateTaskResponse.getCreatedAt());
        assertNotNull(todoistCreateTaskResponse.getId());

    }
}
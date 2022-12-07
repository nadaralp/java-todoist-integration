package com.example.springfirstdemo.todoist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TodoistClientImplTest {
    private static final String BEARER_TOKEN = "5e4154c427a7ce6925160c1fad7415c95f050fc9";
    private static final TodoistCreateTaskRequest createTodoistTask = new TodoistCreateTaskRequest(
            "test from unit test",
            "some test description",
            LocalDate.now().plusDays(2)
    );

    TodoistClientImpl sut;

    @BeforeEach
    void setUp() {
        sut = new TodoistClientImpl();

    }

    @Test
    void addTodoistTask_ShouldAddTaskOnTodoistApp_WhenRequestSent() throws Exception {
        // Arrange

        // Act
        TodoistCreateTaskResponse todoistCreateTaskResponse = sut.addTask(createTodoistTask, BEARER_TOKEN);

        // Assert
        assertNotNull(todoistCreateTaskResponse.getCreatedAt());
        assertNotNull(todoistCreateTaskResponse.getId());

    }


    @Test
    void closeTask_ShouldCloseTask() throws Exception {
        // Arrange
        var addedTask = sut.addTask(createTodoistTask, BEARER_TOKEN);
        assertNotNull(addedTask.getId());

        // Act
        sut.closeTask(addedTask.getId(), BEARER_TOKEN);
        assertDoesNotThrow(() -> {});

        // Assert

    }
}
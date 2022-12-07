package com.example.springfirstdemo.todos;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import com.example.springfirstdemo.common.exceptions.NotFoundException;
import com.example.springfirstdemo.todoist.TodoistClient;
import com.example.springfirstdemo.todoist.TodoistCreateTaskRequest;
import com.example.springfirstdemo.user.AppUser;
import com.example.springfirstdemo.user.AppUserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {
    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final AppUserService userService;
    private final TodoRepository todoRepository;
    private final TodoistClient todoistClient;

    @Autowired
    public TodoService(
            AppUserService userService,
            TodoRepository todoRepository,
            TodoistClient todoistClient) {
        this.userService = userService;
        this.todoRepository = todoRepository;
        this.todoistClient = todoistClient;
    }

    public List<Todo> getUserTodos(UUID userId) {
        // Option 1:
//        log.info("getting todos for user: {}", userId);
//        List<Todo> todos = todoRepository.findAllByUserId(userId);
//        return todos;

        // Option 2
        AppUser user = userService.getById(userId).orElseThrow(() -> new NotFoundException("user with id: %s doesn't exist", userId));
        return user.getTodos();
    }

    @Transactional
    public Todo createTodo(CreateTodoRequest request) {
        log.info("creating todo: {}", request);

        var user = userService.getById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("user with id: %s doesn't exist. Can't create a todo", request.getUserId()));


        if (request.getDueDate() != null && Instant.now().compareTo(request.getDueDate().toInstant()) < 0) {
            throw new BadRequestException("todo due date must be greather than this moment");
        }

        Todo todo = new Todo(
                request.getTask(),
                request.getDescription(),
                false,
                request.getDueDate(),
                user
        );
        todoRepository.save(todo);
        log.info("created todo");

        if (user.getTodoistInfo() != null) {
            log.info("Adding task to todoist");
            var todoistTask = new TodoistCreateTaskRequest(
                    request.getTask(),
                    request.getDescription(),
                    request.getDueDate() != null ? request.getDueDate().toLocalDate() : null
            );
            todoistClient.addTask(todoistTask, user.getTodoistInfo().getTodoistApiKey());
        }

        return todo;
    }

    @Transactional
    public void completeTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException("todo with id: %s doesn't exist", todoId));

        if(todo.isCompleted()) {
            throw new BadRequestException("The todo is already completed");
        }

        // That sets the state in the database because the entity is fetched in a managed state from Hibernate.
        todo.setCompleted(true);
        log.info("completed todo: {}", todoId);
    }
}

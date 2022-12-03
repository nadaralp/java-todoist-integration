package com.example.springfirstdemo.todos;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import com.example.springfirstdemo.common.exceptions.NotFoundException;
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

    @Autowired
    public TodoService(AppUserService userService, TodoRepository todoRepository) {
        this.userService = userService;
        this.todoRepository = todoRepository;
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
    public Todo createTodo(CreateTodoRequest createTodoRequest) {
        log.info("creating todo: {}", createTodoRequest);

        var user = userService.getById(createTodoRequest.getUserId());
        if (user.isEmpty()) {
            throw new NotFoundException("user with id: %s doesn't exist. Can't create a todo", createTodoRequest.getUserId());
        }

        if (createTodoRequest.getDueDate() != null && Instant.now().compareTo(createTodoRequest.getDueDate().toInstant()) < 0) {
            throw new BadRequestException("todo due date must be greather than this moment");
        }

        Todo todo = new Todo(
                createTodoRequest.getTask(),
                createTodoRequest.getDescription(),

                false,
                createTodoRequest.getDueDate(),
                user.get()
        );
        todoRepository.save(todo);
        log.info("created todo");
        return todo;
    }
}

package com.example.springfirstdemo.todos;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<?> getAllByUser(@RequestParam Optional<String> userId) {
        if (userId.isEmpty()) {
            return ResponseEntity.badRequest().body("userId query is required");
        }

        return ResponseEntity.ok().body(
                todoService.getUserTodos(UUID.fromString(userId.get()))
        );
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody @Valid CreateTodoRequest createTodoRequest) {
        Todo createdTodo = todoService.createTodo(createTodoRequest);
        URI serverUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();
        return ResponseEntity.created(URI.create(serverUri + "/todos/" + createdTodo.getId())).body(createdTodo);
    }

    @PostMapping("{id}/complete")
    public ResponseEntity<?> completeTodo(@PathVariable Long id) {
        todoService.completeTodo(id);
        return ResponseEntity.noContent().build();
    }
}



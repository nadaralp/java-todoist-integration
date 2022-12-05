package com.example.springfirstdemo.user.api_models;

import com.example.springfirstdemo.todos.Todo;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppUserDto {
    private UUID id;

    private String name;

    private String email;

    private LocalDate dateOfBirth;

    // todos is not even queried because it's lazy
//    private List<Todo> todos;

}


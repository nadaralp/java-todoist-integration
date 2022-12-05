package com.example.springfirstdemo.user.api_models;

import com.example.springfirstdemo.todos.Todo;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
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

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    // todos is not even queried because it's lazy
//    private List<Todo> todos;

}


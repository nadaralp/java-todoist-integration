package com.example.springfirstdemo.user.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppUserResponse {
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


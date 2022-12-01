package com.example.springfirstdemo.user;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

// Note: the de-facto validation in Spring is hibernate-validator

import java.time.LocalDate;

//TODO: check what spring needs to map data objects
public class CreateUserRequest {
    @NotBlank
    @jakarta.validation.constraints.Size(min = 2)
    private String name;

    @NotBlank(message = "email field is missing")
    @Email
    private String email;

    @NotNull
    private LocalDate dateOfBirth;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}

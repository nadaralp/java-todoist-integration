package com.example.springfirstdemo.user;

import java.time.LocalDate;

//TODO: check what spring needs to map data objects
public class CreateUserRequest {
    private String name;
    private String email;
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

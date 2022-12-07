package com.example.springfirstdemo.user.dto;

import java.time.LocalDate;

public class UpdateUserRequest {
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
        return "UpdateUserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}

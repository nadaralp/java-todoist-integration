package com.example.springfirstdemo.common.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, Object... objects) {
        super(String.format(message, objects));
    }

    public NotFoundException(String message) {
        super(message);
    }
}

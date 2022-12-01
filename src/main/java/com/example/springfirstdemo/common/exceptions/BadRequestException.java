package com.example.springfirstdemo.common.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message, Object... objects) {
        super(String.format(message, objects));
    }

    public BadRequestException(String message) {
        super(message);
    }
}

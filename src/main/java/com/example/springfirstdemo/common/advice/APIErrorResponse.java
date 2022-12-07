package com.example.springfirstdemo.common.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;


@RequiredArgsConstructor
@Getter
public class APIErrorResponse {
    private OffsetDateTime timestamp = OffsetDateTime.now();
    private final int status;
    private final String message;

}

package com.example.springfirstdemo.common.advice;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import com.example.springfirstdemo.common.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        log.error("validation error", exception);
        Map<String, String> errorsMap = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                                FieldError::getField,
                                FieldError::getDefaultMessage
                        )
                );
        return ResponseEntity.badRequest().body(errorsMap);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<Map<String, String>> notFoundExceptionHandler(NotFoundException exception) {
        log.error("not found error", exception);
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        new HashMap<>() {
                            {
                                put("timestamp", OffsetDateTime.now().toString());
                            }

                            {
                                put("status", "404");
                            }

                            {
                                put("message", exception.getMessage());
                            }

                        }
                );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public APIErrorResponse handleBadRequestException(BadRequestException exception) {
        log.error("Caught bad request exception", exception);
        return new APIErrorResponse(400, exception.getMessage());
    }
}

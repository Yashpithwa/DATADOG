package com.example.Datadog.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex){

        return ResponseEntity.badRequest().body(
                Map.of(
                        "error",ex.getMessage(),
                        "time", LocalDateTime.now()
                )
        );
    }
}

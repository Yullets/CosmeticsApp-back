package com.cosmetics.app.controller.impl;

import com.cosmetics.app.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> handlerEntityNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handlerBadRequestException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

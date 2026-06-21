package com.duoc.pporden.exception;

import com.duoc.pporden.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //maneja el notfound
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarNotFound(
        ResourceNotFoundException ex) {

    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage());

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(error);
    }

    //maneja el badrequest
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> manejarBadRequest(
        BadRequestException ex) {

    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage());

    return ResponseEntity
        .badRequest()
        .body(error);
    }

    //maneja el expetion general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarException(
        Exception ex) {

    ErrorResponse error =
            new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage());

    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(error);
    }
}
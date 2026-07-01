package com.duoc.pporden.exception;

import com.duoc.pporden.dto.ErrorResponse;

import feign.RetryableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(
        MethodArgumentNotValidException ex) {

    String mensaje = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getDefaultMessage())
        .findFirst()
        .orElse("Error de validacion");

    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            mensaje);

    return ResponseEntity
        .badRequest()
        .body(error);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ErrorResponse> manejarServicioNoDisponible(
        RetryableException ex) {

    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.SERVICE_UNAVAILABLE.value(),
            "El servicio externo no está disponible en este momento");

    return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonIncorrecto(
        HttpMessageNotReadableException ex){

    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "El cuerpo de la solicitud contiene datos inválidos.");

    return ResponseEntity
        .badRequest()
        .body(error);
    }

}
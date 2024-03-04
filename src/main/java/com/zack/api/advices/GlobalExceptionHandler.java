package com.zack.api.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.bodies.ResponseErrorValidator;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponseErrorValidator responseErrorValidator=new ResponseErrorValidator("dados inválidos");
        ex.getBindingResult().getAllErrors().forEach(error -> {

            String errorMessage = error.getDefaultMessage();
            responseErrorValidator.addErros(errorMessage);
        });
        return ResponseEntity.badRequest().body(responseErrorValidator);
    }

}
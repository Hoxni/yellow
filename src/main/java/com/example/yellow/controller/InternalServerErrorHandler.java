package com.example.yellow.controller;

import com.example.yellow.entity.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class InternalServerErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorInfo> handleRequest(HttpServletRequest req, RuntimeException e){

        String errorUrl = req.getRequestURL().toString();

        ErrorInfo errorInfo = ErrorInfo.builder()
                .message(e.getMessage() != null
                        ? e.getMessage()
                        : "Something went wrong")
                .url(errorUrl)
                .build();
        errorInfo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}

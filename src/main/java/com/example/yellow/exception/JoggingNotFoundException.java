package com.example.yellow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JoggingNotFoundException extends RuntimeException {
    public JoggingNotFoundException(Long id) {
        super("No jogging with id: " + id);
    }
}

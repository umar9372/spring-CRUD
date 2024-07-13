package com.example.springg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateResourcException extends RuntimeException {
    public DuplicateResourcException(String message) {
        super(message);
    }
}

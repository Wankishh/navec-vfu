package com.navec.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;


@Getter
public class ResponseException extends RuntimeException {
    private final HttpStatus statusCode;
    private final Map<String, String> errors;

    public ResponseException (HttpStatus statusCode) {
        super();
        this.statusCode = statusCode;
        this.errors = new HashMap<>();
    }

    public ResponseException (HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.errors = new HashMap<>();
    }

    public ResponseException (HttpStatus statusCode, String message, Map<String, String> errors) {
        super(message);
        this.statusCode = statusCode;
        this.errors = errors;
    }
}

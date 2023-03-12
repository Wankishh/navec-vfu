package com.navec.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ResponseException extends RuntimeException {
    private final HttpStatus statusCode;

    public ResponseException (HttpStatus statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public ResponseException (HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}

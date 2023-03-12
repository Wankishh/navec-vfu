package com.navec.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception,
                                                            WebRequest ignoredRequest) {
        ValidationExceptionResponse validationExceptionResponse = new ValidationExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                new HashMap<>());

        return new ResponseEntity<>(validationExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseException.class)
    public final ResponseEntity<Object> handleResponseException(ResponseException exception, WebRequest request) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());
        return new ResponseEntity<>(errors, exception.getStatusCode());
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders ignoredHeaders,
                                                                  HttpStatus ignoredStatus,
                                                                  WebRequest ignoredRequest) {
        return getErrorsMap(exception);
    }

    private ResponseEntity<Object> getErrorsMap(MethodArgumentNotValidException exception) {
        HashMap<String, String> errors = new HashMap<>();
        exception.getFieldErrors()
                        .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        ValidationExceptionResponse validationExceptionResponse = new ValidationExceptionResponse(LocalDateTime.now(),
                "Validation Failed",
                errors);

        return new ResponseEntity<>(validationExceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // TODO: Better response for error fields
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return getErrorsMap(ex);
    }
}

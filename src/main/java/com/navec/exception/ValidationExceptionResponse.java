package com.navec.exception;

import java.time.LocalDateTime;
import java.util.HashMap;

public record ValidationExceptionResponse(LocalDateTime timestamp,
                                          String message,
                                          HashMap<String, String> errors) {

}

package com.ehr.authenticate.exceptionhandeler;

import com.ehr.authenticate.config.MessageSourceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSourceFactory messageSourceFactory;

    public GlobalExceptionHandler(MessageSourceFactory messageSourceFactory) {
        this.messageSourceFactory = messageSourceFactory;
    }

    // Handle validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String messageKey = error.getDefaultMessage(); // should be a key like "auth.invalid"
            String localizedMessage = messageSourceFactory.getMessage(messageKey);
            errors.put(field, localizedMessage);
        });

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", messageSourceFactory.getMessage("error.validation"));
        response.put("messages", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle custom AuthException (message is message key)
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> handleAuthException(AuthException ex) {
        String key = ex.getMessage(); // expecting a key like "auth.invalid"
        String localizedMessage = messageSourceFactory.getMessage(key);

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", localizedMessage);
        response.put("message", localizedMessage);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Handle custom UserNotFound (message is message key)
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFound ex) {
        String key = ex.getMessage(); // expecting a key like "auth.userNotFound"
        String localizedMessage = messageSourceFactory.getMessage(key);

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", localizedMessage);
        response.put("message", localizedMessage);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Generic Exception (message is assumed to be message key)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        String key = ex.getMessage(); // Use message key directly
        String localizedMessage = messageSourceFactory.getMessage(key);

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", messageSourceFactory.getMessage("error.internal"));
        response.put("message", localizedMessage);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

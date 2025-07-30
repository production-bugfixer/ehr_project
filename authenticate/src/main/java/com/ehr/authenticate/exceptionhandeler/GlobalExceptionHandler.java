package com.ehr.authenticate.exceptionhandeler;

import com.ehr.authenticate.config.MessageSourceFactory;
import com.ehr.authenticate.dto.GlobalResponse;
import com.ehr.authenticate.dto.GlobalEncryptedResponse;
import com.ehr.authenticate.util.ObjectEncryptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSourceFactory messageSourceFactory;

    public GlobalExceptionHandler(MessageSourceFactory messageSourceFactory) {
        this.messageSourceFactory = messageSourceFactory;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalEncryptedResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String messageKey = error.getDefaultMessage();
            String localizedMessage = messageSourceFactory.getMessage(messageKey);
            errors.put(field, localizedMessage);
        });

        GlobalResponse<Map<String, String>> response = new GlobalResponse<>();
        String message = messageSourceFactory.getMessage("error.validation");
        response.setStatus(false);
        response.setNotificationMessage(message);
        response.setMessage(message);
        response.setErrorCode(null);
        response.setExceptionMessage(null);
        response.setLanguage(messageSourceFactory.getLanguage());
        response.setData(errors);

        GlobalEncryptedResponse encrypted = ObjectEncryptor.encryptFields(response);
        return new ResponseEntity<>(encrypted, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        OPTNotMatchingException.class,
        RequestIdNotFoundException.class,
        AuthException.class,
        UserNotFound.class,
        OTPExpieredException.class
    })
    public ResponseEntity<GlobalEncryptedResponse> handleCustomExceptions(RuntimeException ex) {
        String localizedMessage = messageSourceFactory.getMessage(ex.getMessage());

        GlobalResponse<String> response = new GlobalResponse<>();
        response.setStatus(false);
        response.setNotificationMessage(localizedMessage);
        response.setMessage(localizedMessage);
        response.setErrorCode(null);
        response.setExceptionMessage(null);
        response.setLanguage(messageSourceFactory.getLanguage());
        response.setData(null);

        GlobalEncryptedResponse encrypted = ObjectEncryptor.encryptFields(response);
        return new ResponseEntity<>(encrypted, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalEncryptedResponse> handleGeneralException(Exception ex) {
        String messageKey = ex.getMessage();
        String localizedMessage = messageSourceFactory.getMessage(messageKey);

        GlobalResponse<String> response = new GlobalResponse<>();
        response.setStatus(false);
        response.setNotificationMessage(messageSourceFactory.getMessage("error.internal"));
        response.setMessage(localizedMessage);
        response.setErrorCode(null);
        response.setExceptionMessage(ex.getClass().getSimpleName());
        response.setLanguage(messageSourceFactory.getLanguage());
        response.setData(null);

        GlobalEncryptedResponse encrypted = ObjectEncryptor.encryptFields(response);
        return new ResponseEntity<>(encrypted, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

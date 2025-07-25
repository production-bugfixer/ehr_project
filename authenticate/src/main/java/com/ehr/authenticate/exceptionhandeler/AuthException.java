package com.ehr.authenticate.exceptionhandeler;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
    	super(message);
    }
}

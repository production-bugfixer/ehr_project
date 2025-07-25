package com.ehr.authenticate.exceptionhandeler;

public class UserNotFound extends RuntimeException {
	public UserNotFound(String message) {
    	super(message);
    }
}

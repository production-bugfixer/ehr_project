package com.ehr.authenticate.exceptionhandeler;

public class RequestIdNotFoundException extends RuntimeException  {
	public RequestIdNotFoundException() {
    	super("request.requestIdNotFoundException");
    }

}

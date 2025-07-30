package com.ehr.authenticate.exceptionhandeler;

public class OPTNotMatchingException extends RuntimeException  {
	public OPTNotMatchingException() {
    	super("request.otpNotMatching");
    }
}

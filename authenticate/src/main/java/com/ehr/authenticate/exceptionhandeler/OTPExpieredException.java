package com.ehr.authenticate.exceptionhandeler;

public class OTPExpieredException extends RuntimeException{
	public OTPExpieredException(){
		super("request.otpExpired");
	}

}

package com.ehr.authenticate.dto;

import com.ehr.authenticate.config.MessageSourceFactory;

public class ForgetPasswordReponse {
	private String requestId;
    private String message;
	public ForgetPasswordReponse(String requestId,String messageKey) {
	    this.requestId = requestId;
	    this.message =messageKey;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

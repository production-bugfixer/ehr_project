package com.ehr.authenticate.dto;

import com.ehr.authenticate.config.MessageSourceFactory;

public class ForgetPasswordReponse {
	private String requestId;
    private String message;
	public ForgetPasswordReponse(String requestId,String messageKey, MessageSourceFactory messageSourceFactory) {
	    this.requestId = requestId;
	    this.message = messageSourceFactory.getMessage(messageKey);
	}
}

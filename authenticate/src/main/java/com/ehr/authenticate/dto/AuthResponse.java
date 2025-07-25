package com.ehr.authenticate.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.ehr.authenticate.config.MessageSourceFactory;

public class AuthResponse {
    private String token;
    private String userType;
    private String messageKey;
    private MessageSourceFactory messageSourceFactory;
public AuthResponse(String token, String userType, String userName, String messageKey, MessageSourceFactory messageSourceFactory) {
    this.token = token;
    this.userType = userType;
    this.messageKey = messageSourceFactory.getMessage(messageKey);
}

    public String getToken() {
        return token;
    }

    public String getUserType() {
        return userType;
    }

    public String getToasterMessage() {
        return messageKey;
    }
}


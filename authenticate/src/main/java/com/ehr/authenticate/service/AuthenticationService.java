package com.ehr.authenticate.service;

import org.springframework.stereotype.Service;

import ehr.model.Users.EHRUser;

@Service
public class AuthenticationService {
    public String validate(EHRUser user) {
    	
    	return user.getUsername();
    }
    
}

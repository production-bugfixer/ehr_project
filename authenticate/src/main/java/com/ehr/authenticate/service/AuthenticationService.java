package com.ehr.authenticate.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ehr.authenticate.config.JwtUtil;
import com.ehr.authenticate.entity.EHRUserEntity;
import com.ehr.authenticate.exceptionhandeler.AuthException;
import com.ehr.authenticate.exceptionhandeler.UserNotFound;
import com.ehr.authenticate.repo.EHRUserRepository;

import ehr.model.Users.EHRUser;

@Service
public class AuthenticationService {
	private EHRUserRepository repo;
	private JwtUtil jwt;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	public AuthenticationService(EHRUserRepository repo,JwtUtil jwt) {
		this.jwt=jwt;
		this.repo=repo;
		
	}
    public String validatePassword(EHRUser user) {
    	  Optional<EHRUserEntity> optionalUser = repo.findByUsernameOrEmail(user.getUsername(), user.getEmail());

          if (optionalUser.isPresent()) {
              EHRUserEntity userEntity = optionalUser.get();
              boolean passwordMatch=user.getPassword().equals(userEntity.getPassword());
              if (passwordMatch) {
            	  Map<String, Object> claims = new HashMap<>();
            	  userEntity.setPassword(null);
                  claims.put("email", user.getEmail());
                  claims.put("user", userEntity);
     
                  return jwt.createToken(claims,userEntity.getEmail());
              } else {
                  throw new AuthException("auth.invalid");
              }
          } else {
              throw new UserNotFound("auth.userNotFound");
          }
    }
    public String issueNewToken(String token) {
        try {
           
        	   return this.jwt.refreshToken(token);
           
        } catch (Exception e) {
            return null;
        }
        
    }
    public boolean validateToken(String token) {
        try {
            return !jwt.extractExpiration(token).before(new java.util.Date());
        } catch (Exception e) {
            return false;
        }
    }
    
}

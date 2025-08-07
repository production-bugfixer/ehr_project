package com.ehr.authenticate.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ehr.authenticate.dto.AuthResponse;
import com.ehr.authenticate.dto.EncryptedModel;
import com.ehr.authenticate.dto.GlobalEncryptedResponse;
import com.ehr.authenticate.dto.GlobalResponse;
import com.ehr.authenticate.service.AuthenticationService;
import com.ehr.authenticate.util.ObjectEncryptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ehr.encrypt.aes.AESDecryptor;

import ehr.model.Users.*;

@RestController
@RequestMapping("/auth/v1")
//@CrossOrigin(origins = "*")
public class AuthController extends Controller {
    
    private final AuthenticationService authService;

    @Autowired
    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/doctor")
    public ResponseEntity<GlobalEncryptedResponse> authenticateDoctor(@RequestBody EncryptedModel model, @RequestHeader(name = "Lang", required = false) String lang) throws JsonMappingException, JsonProcessingException {
    	String decryptedJson = AESDecryptor.decrypt(model.getData());
        ObjectMapper mapper = new ObjectMapper();
        Doctor user = mapper.readValue(decryptedJson, Doctor.class);
        String token = authService.validatePassword(user);
        GlobalResponse<String> response=new GlobalResponse<>();
        String notication=messageSourceFactory.getMessage("auth.success");
        response.setData(token);
        response.setErrorCode(null);
        response.setNotificationMessage(notication);
        response.setStatus(true);
        response.setMessage(notication);
        response.setLanguage(messageSourceFactory.getLanguage());
        GlobalEncryptedResponse reply=ObjectEncryptor.encryptFields(response);
        return ResponseEntity.ok(reply);
    }

    @PostMapping("/patient")
    public ResponseEntity<AuthResponse> authenticatePatient(@Valid @RequestBody Patient user, @RequestHeader(name = "lang", required = false) String lang) {
        
        return ResponseEntity.ok(new AuthResponse("dummyToken", "PATIENT",user.getUsername() ,"auth.success",messageSourceFactory));
    }

    @PostMapping("/nurse")
    public ResponseEntity<AuthResponse> authenticateNurse(@Valid @RequestBody Nurse user, @RequestHeader(name = "lang", required = false) String lang) {
        
        return ResponseEntity.ok(new AuthResponse("dummyToken", "NURSE", user.getUsername(),"auth.success",messageSourceFactory));
    }

    @PostMapping("/technician")
    public ResponseEntity<AuthResponse> authenticateTechnician(@Valid @RequestBody Technician user, @RequestHeader(name = "lang", required = false) String lang) {
        
        return ResponseEntity.ok(new AuthResponse("dummyToken", "TECHNICIAN", user.getUsername(),"auth.success",messageSourceFactory));
    }

    @PostMapping("/adminstaff")
    public ResponseEntity<AuthResponse> authenticateAdmin(@Valid @RequestBody AdminStaff user, @RequestHeader(name = "lang", required = false) String lang) {
        
        return ResponseEntity.ok(new AuthResponse("dummyToken", "ADMIN",user.getUsername(), "auth.success",messageSourceFactory));
    }

    @PostMapping("/ex")
    public ResponseEntity<AuthResponse> authenticateEx(@Valid @RequestBody ExternalUser user, @RequestHeader(name = "lang", required = false) String lang) {
        
        return ResponseEntity.ok(new AuthResponse("dummyToken", "EXTERNAL",user.getUsername(), "auth.success",messageSourceFactory));
    }
}

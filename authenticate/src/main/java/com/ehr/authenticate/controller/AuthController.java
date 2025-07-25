package com.ehr.authenticate.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ehr.authenticate.dto.AuthResponse;
import com.ehr.authenticate.service.AuthenticationService;

import ehr.model.Users.*;

@RestController
@RequestMapping("/auth/v1")
public class AuthController extends Controller {
    
    private final AuthenticationService authService;

    @Autowired
    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/doctor")
    public ResponseEntity<AuthResponse> authenticateDoctor(@Valid @RequestBody Doctor user, @RequestHeader(name = "lang", required = false) String lang) {
        String token = authService.validatePassword(user);      
        return ResponseEntity.ok(new AuthResponse(token, "DOCTOR",user.getUsername(),"auth.success",messageSourceFactory));
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

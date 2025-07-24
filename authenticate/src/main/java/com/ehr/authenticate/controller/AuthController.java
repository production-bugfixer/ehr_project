package com.ehr.authenticate.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehr.authenticate.service.AuthenticationService;

import ehr.model.Users.AdminStaff;
import ehr.model.Users.Doctor;
import ehr.model.Users.EHRUser;
import ehr.model.Users.ExternalUser;
import ehr.model.Users.Nurse;
import ehr.model.Users.Patient;
import ehr.model.Users.Technician;

@RestController
@RequestMapping("auth/v1")
public class AuthController {
	AuthenticationService authService;
	@Autowired
	AuthController(AuthenticationService authService){
		this.authService=authService;
	}
	
	@PostMapping("/auth/doctor")
    public String authenticateDoctor(@Valid @RequestBody Doctor user) {
		EHRUser doctor=user;
        return authService.validate(user);
    }
	@PostMapping("/auth/patient")
    public String authenticatePatient(@Valid @RequestBody Patient user) {
		EHRUser patient=user;
        return "User validated successfully"+user.getUsername();
    }
	@PostMapping("/auth/nurse")
    public String authenticateNurse(@Valid @RequestBody Nurse user) {
		EHRUser nurse=user;
        return "User validated successfully"+user.getUsername();
    }
	@PostMapping("/auth/technician")
    public String authenticateTechnician(@Valid @RequestBody Technician user) {
		EHRUser technician=user;
        return "User validated successfully"+user.getUsername();
    }
	@PostMapping("/auth/adminstaff")
    public String authenticateAdmin(@Valid @RequestBody AdminStaff user) {
		EHRUser admin=user;
        return "User validated successfully"+user.getUsername();
    }
	@PostMapping("/auth/ex")
    public String authenticateEx(@Valid @RequestBody ExternalUser user) {
		EHRUser extUser=user;
        return "User validated successfully"+user.getUsername();
    }

}

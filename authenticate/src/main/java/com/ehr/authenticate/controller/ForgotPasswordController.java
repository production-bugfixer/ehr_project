package com.ehr.authenticate.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehr.authenticate.dto.AuthResponse;
import com.ehr.authenticate.dto.ForgetPasswordReponse;
import com.ehr.authenticate.dto.ResetPasswordRequest;
import com.ehr.authenticate.service.AuthenticationService;
import com.ehr.authenticate.service.ForgetPasswordService;

import ehr.model.Users.Doctor;
import ehr.model.miscellaneous.ForgetPasswordModel;

@RestController
@RequestMapping("forgort-password")
public class ForgotPasswordController extends Controller{
	private final ForgetPasswordService forgetPasswordService;
    @Autowired
	public ForgotPasswordController(ForgetPasswordService forgetPasswordService) {
		this.forgetPasswordService=forgetPasswordService;
	}
    @PostMapping("/request")
    public ResponseEntity<ForgetPasswordReponse> triggerVerification(@Valid @RequestBody ForgetPasswordModel forgetPasswordModel, @RequestHeader(name = "lang", required = false) String lang) {
    	Long id=forgetPasswordService.getOTP(forgetPasswordModel);
    	 return ResponseEntity.ok(new ForgetPasswordReponse(String.valueOf(id),"OTP sent successfully"));
    }
    @PostMapping("/reset")
    public ResponseEntity<Boolean> rest(@Valid @RequestBody ResetPasswordRequest resetModel, @RequestHeader(name = "lang", required = false) String lang) {
    	Boolean isResetCompleted=forgetPasswordService.rest(resetModel);
    	 return ResponseEntity.ok(true);
    }
	
}

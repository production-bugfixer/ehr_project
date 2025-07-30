package com.ehr.authenticate.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehr.authenticate.dto.AuthResponse;
import com.ehr.authenticate.dto.EncryptedModel;
import com.ehr.authenticate.dto.ForgetPasswordReponse;
import com.ehr.authenticate.dto.GlobalEncryptedResponse;
import com.ehr.authenticate.dto.GlobalResponse;
import com.ehr.authenticate.dto.ResetPasswordRequest;
import com.ehr.authenticate.service.AuthenticationService;
import com.ehr.authenticate.service.ForgetPasswordService;
import com.ehr.authenticate.util.ObjectEncryptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ehr.encrypt.aes.AESDecryptor;
import ehr.encrypt.aes.AESUtil;
import ehr.model.Users.Doctor;
import ehr.model.miscellaneous.ForgetPasswordModel;

@RestController
@RequestMapping("forgort-password")
@CrossOrigin(origins = "*")
public class ForgotPasswordController extends Controller{
	private final ForgetPasswordService forgetPasswordService;
    @Autowired
	public ForgotPasswordController(ForgetPasswordService forgetPasswordService) {
		this.forgetPasswordService=forgetPasswordService;
	}
    @PostMapping("/request")
    public ResponseEntity<GlobalEncryptedResponse> triggerVerification(@RequestBody EncryptedModel model, @RequestHeader(name = "lang", required = false) String lang) throws JsonMappingException, JsonProcessingException {
    	String decryptedJson = AESDecryptor.decrypt(model.getData());
        ObjectMapper mapper = new ObjectMapper();
       ForgetPasswordModel forgetPasswordModel = mapper.readValue(decryptedJson, ForgetPasswordModel.class);
    	Long id=forgetPasswordService.getOTP(forgetPasswordModel);
    	GlobalResponse<ForgetPasswordReponse> response=new GlobalResponse<>();
        String notication=messageSourceFactory.getMessage("request.success");
        response.setData(new ForgetPasswordReponse(String.valueOf(id),"OTP sent successfully"));
        
        //Object encryptedData = AESUtil.encryptRecursively(new ForgetPasswordReponse(String.valueOf(id),"OTP sent successfully")); // returns a Map
        // Encrypted data set here
        response.setErrorCode(null);
        response.setNotificationMessage(messageSourceFactory.getMessage("request.success"));
        response.setStatus(true);
        response.setMessage("OTP encrypted and sent");
        response.setLanguage(messageSourceFactory.getLanguage());
        GlobalEncryptedResponse reply=ObjectEncryptor.encryptFields(response);
    	return ResponseEntity.ok(reply);
    }
    @PostMapping("/reset")
    public ResponseEntity<GlobalResponse> rest(@Valid @RequestBody ResetPasswordRequest resetModel, @RequestHeader(name = "lang", required = false) String lang) {
    	Boolean isResetCompleted=forgetPasswordService.rest(resetModel);
    	GlobalResponse<Boolean> response=new GlobalResponse<>();
        String notication=messageSourceFactory.getMessage("request.restSuccessfull");
        response.setData(true);
        response.setErrorCode(null);
        response.setNotificationMessage(notication);
        response.setStatus(true);
        response.setMessage(notication);
        response.setLanguage(messageSourceFactory.getLanguage());
    	return ResponseEntity.ok(response);
    }
	
}

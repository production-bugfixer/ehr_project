package com.ehr.authenticate.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ehr.authenticate.dto.ResetPasswordRequest;
import com.ehr.authenticate.entity.EHRUserEntity;
import com.ehr.authenticate.entity.OtpRequestEntity;
import com.ehr.authenticate.exceptionhandeler.UserNotFound;
import com.ehr.authenticate.repo.EHRUserRepository;
import com.ehr.authenticate.repo.OtpRequestRepository;

import ehr.model.Users.EHRUser;
import ehr.model.miscellaneous.ForgetPasswordModel;

@Service
public class ForgetPasswordService {

    @Autowired
    private OtpRequestRepository otpRepo;

    @Autowired
    private EHRUserRepository userRepo;

    private final String MAIL_SERVICE_URL = "http://147.79.66.20:9999/sendmail.php";

    public Long getOTP(ForgetPasswordModel forgetModel) {
        Optional<EHRUserEntity> optionalUser = userRepo.findByEmail(forgetModel.getEhrId());

        if (optionalUser.isEmpty()) {
            throw new UserNotFound("auth.userNotFound");
        }

        EHRUserEntity user = optionalUser.get();

        // Generate OTP and Request ID
        String otp = generateOTP();
        String requestId = generateRequestId();

        // Save OTP to database
        OtpRequestEntity otpEntity = new OtpRequestEntity();
        otpEntity.setOtp(otp);
        otpEntity.setRequestId(requestId);
        otpEntity.setEhrId(user.getEmail());
        otpEntity.setCreatedAt(LocalDateTime.now());
        otpEntity.setExpiresAt(otpEntity.getCreatedAt().plusMinutes(10));
        otpEntity.setMethod(
            "phone".equalsIgnoreCase(forgetModel.getVerificationMethod()) ? "phone" : "email"
        );

        otpRepo.save(otpEntity);

        // Send mail to user
        sendEmailToPHP(otp, user.getEmail(), requestId, user.getUsername());

        return Long.parseLong(requestId);
    }

    private String generateOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit
    }

    private String generateRequestId() {
        return String.valueOf(1000 + new Random().nextInt(9000)); // 4-digit
    }

    private void sendEmailToPHP(String otp, String email, String requestId, String username) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = new HashMap<>();
        payload.put("emailto", email);
        payload.put("otp", otp);
        payload.put("username", username);
        payload.put("requestid", requestId);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(MAIL_SERVICE_URL, request, String.class);
            System.out.println("Email Response from PHP: " + response.getStatusCode() + " - " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

	public Boolean rest(@Valid ResetPasswordRequest resetModel) {
		Optional<OtpRequestEntity> optentity=otpRepo.findByRequestId(resetModel.getRequestId());
		if(optentity.isPresent()) {
			OtpRequestEntity optObject=optentity.get();
			if(optObject.getOtp().equals(resetModel.getOtp())){
				//update password
				String email=optObject.getEhrId();
				Optional<EHRUserEntity> ehrObjectOption=userRepo.findByEmail(email);
				if(ehrObjectOption.isPresent()) {
					EHRUserEntity ehrObject=ehrObjectOption.get();
					ehrObject.setPassword(resetModel.getNewPassword());
					userRepo.save(ehrObject);
					otpRepo.deleteByEhrId(email);
					return true;
				}
			}
		}else {
			return false;
		}
		return false;
	}
}

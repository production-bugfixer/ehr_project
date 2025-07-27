package com.ehr.authenticate.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ehr.authenticate.entity.EHRUserEntity;
import com.ehr.authenticate.entity.OtpRequestEntity;
import com.ehr.authenticate.exceptionhandeler.UserNotFound;
import com.ehr.authenticate.repo.EHRUserRepository;
import com.ehr.authenticate.repo.OtpRequestRepository;

import ehr.model.miscellaneous.ForgetPasswordModel;

@Service
public class ForgetPasswordService {

    @Autowired
    private OtpRequestRepository otpRepo;

    @Autowired
    private EHRUserRepository userRepo;

    private final String url = "http://147.79.66.20:9999/sendmail.php";

    public Long getOTP(ForgetPasswordModel forgetModel) {
        Optional<EHRUserEntity> optionalUser = userRepo.findByEmail(forgetModel.getEhrId());
        if (optionalUser.isEmpty()) {
                throw new UserNotFound("auth.userNotFound");
            
        }

        EHRUserEntity user = optionalUser.get();

        // Generate OTP and Request ID
        String otp = String.valueOf((long) (Math.random() * 900000) + 100000);
        String requestId = String.valueOf((long) (Math.random() * 9000) + 1000);

        // Set OTP details
        OtpRequestEntity otpEntity = new OtpRequestEntity();
        otpEntity.setOtp(otp);
        otpEntity.setRequestId(requestId);
        otpEntity.setEhrId(user.getEmail());
        otpEntity.setCreatedAt(LocalDateTime.now());
        otpEntity.setExpiresAt(otpEntity.getCreatedAt().plusMinutes(10));
        otpEntity.setMethod(
            forgetModel.getVerificationMethod().equalsIgnoreCase("phone") ? "phone" : "email"
        );

        // Save to database
        otpRepo.save(otpEntity);

        // Send mail via PHP script
        sendEmailToPHP(otp, user.getEmail(), requestId, user.getUsername());

        return Long.parseLong(requestId);
    }

    private void sendEmailToPHP(String otp, String email, String requestId, String username) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> jsonPayload = new HashMap<>();
        jsonPayload.put("emailto", email);
        jsonPayload.put("otp", otp);
        jsonPayload.put("username", username);
        jsonPayload.put("requestid", requestId);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(jsonPayload, headers);

        try {
            ResponseEntity<String> response =
                restTemplate.postForEntity(url, requestEntity, String.class);
            System.out.println("PHP Mail Response: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace(); // Use logger in production
        }
    }

}

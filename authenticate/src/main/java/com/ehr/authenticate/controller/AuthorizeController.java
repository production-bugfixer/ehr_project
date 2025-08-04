package com.ehr.authenticate.controller;

import com.ehr.authenticate.dto.GlobalEncryptedResponse;
import com.ehr.authenticate.dto.GlobalResponse;
import com.ehr.authenticate.service.AuthenticationService;
import com.ehr.authenticate.util.ObjectEncryptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorize/v1")
@CrossOrigin(origins = "*")
public class AuthorizeController extends Controller {

    private final AuthenticationService authService;

    @Autowired
    public AuthorizeController(AuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/isAuthorized")
    public ResponseEntity<GlobalEncryptedResponse> isAuthorized(
            @RequestHeader(name = "Lang", required = false) String lang,
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader
    ) throws JsonProcessingException {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return unauthorizedResponse("auth.token.missing");
        }

        String token = authorizationHeader.substring(7);
        // Remove "Bearer "
         // Plug your real logic here

        if (!authService.validateToken(token)) {
            return unauthorizedResponse("auth.token.invalid");
        }
        String newToken = authService.issueNewToken(token);
        GlobalResponse<String> response = new GlobalResponse<>();
        response.setStatus(true);
        response.setMessage(null);
        response.setNotificationMessage(null);
        response.setData(newToken);
        response.setLanguage(messageSourceFactory.getLanguage());

        return ResponseEntity.ok(ObjectEncryptor.encryptFields(response));
    }

    private ResponseEntity<GlobalEncryptedResponse> unauthorizedResponse(String messageKey) throws JsonProcessingException {
        GlobalResponse<String> errorResponse = new GlobalResponse<>();
        errorResponse.setStatus(false);
        errorResponse.setMessage(messageSourceFactory.getMessage(messageKey));
        errorResponse.setNotificationMessage(messageSourceFactory.getMessage(messageKey));
        errorResponse.setData(null);
        errorResponse.setLanguage(messageSourceFactory.getLanguage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ObjectEncryptor.encryptFields(errorResponse));
    }
}

package com.ehr.authenticate;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ehr.model.Users.EHRUser;

@RestController
@RequestMapping("auth")
public class AuthController {
	
	@PostMapping("/authv1")
    public String authenticate(@Valid @RequestBody EHRUser user) {
        return "User validated successfully"+user.getUsername();
    }

}

package ehr.model.Users;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ehr.validators.annotations.method.ValidPhoneNumber;

@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "userType",
	    visible = true
	)
	@JsonSubTypes({
	    @JsonSubTypes.Type(value = Doctor.class, name = "DOCTOR"),
	    @JsonSubTypes.Type(value = Patient.class, name = "PATIENT")
	})
public sealed abstract class EHRUser permits Doctor, Patient {
	private Long id;

    private String username;

    private String email;

    private String password;
    @ValidPhoneNumber
    private String phoneNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
}

// EHRUser.java (updated)
package ehr.model.Users;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ehr.model.utility.Address;
import ehr.validators.annotations.method.ValidPhoneNumber;

@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "userType",
	    visible = true
	)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Doctor.class, name = "DOCTOR"),
    @JsonSubTypes.Type(value = Patient.class, name = "PATIENT"),
    @JsonSubTypes.Type(value = Nurse.class, name = "NURSE"),
    @JsonSubTypes.Type(value = AdminStaff.class, name = "ADMIN_STAFF"),
    @JsonSubTypes.Type(value = Technician.class, name = "TECHNICIAN"),
    @JsonSubTypes.Type(value = ExternalUser.class, name = "EXTERNAL_USER")
})
public sealed abstract class EHRUser permits Doctor, Patient, Nurse, AdminStaff, Technician, ExternalUser {
    private Long id;
    private String username;
    private String email;
    private String password;
    @ValidPhoneNumber
    private String phoneNumber;
    private Address address;
    private boolean active;
    private String profilePictureUrl;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
    
}
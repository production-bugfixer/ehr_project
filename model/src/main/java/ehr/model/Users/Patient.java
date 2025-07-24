// Patient.java (expanded)
package ehr.model.Users;

import ehr.model.allergy.Allergy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public non-sealed class Patient extends EHRUser {
    private String bloodGroup;
    private List<Allergy> allergies = new ArrayList<>();
    private String emergencyContact;
    private LocalDate dateOfBirth;
    private String gender;
    private String maritalStatus;
    //private List<MedicalRecord> medicalRecords = new ArrayList<>();
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public List<Allergy> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
    
    
}
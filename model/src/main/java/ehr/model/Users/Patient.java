package ehr.model.Users;

import java.util.ArrayList;
import java.util.List;

import ehr.model.allergy.Allergy;

public class Patient extends EHRUser {
	private String bloodGroup;
    private List<Allergy> allergies=new ArrayList<>();
    private String emergencyContact;
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
    
    
}

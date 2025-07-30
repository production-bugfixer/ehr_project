// Doctor.java (expanded)
package ehr.model.Users;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ehr.model.utility.Department;
import ehr.model.utility.Qualification;
import ehr.model.utility.Specialization;
import ehr.model.utility.TimeSlot;
@JsonIgnoreProperties(ignoreUnknown = true)
public non-sealed class Doctor extends EHRUser {
    private Specialization specialization;
    private int yearsOfExperience;
    private String licenseNumber;
    private Department department;
    private List<Qualification> qualifications;
    private List<TimeSlot> availableSlots;
    private boolean onCall;
	public Specialization getSpecialization() {
		return specialization;
	}
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}
	public int getYearsOfExperience() {
		return yearsOfExperience;
	}
	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public List<Qualification> getQualifications() {
		return qualifications;
	}
	public void setQualifications(List<Qualification> qualifications) {
		this.qualifications = qualifications;
	}
	public List<TimeSlot> getAvailableSlots() {
		return availableSlots;
	}
	public void setAvailableSlots(List<TimeSlot> availableSlots) {
		this.availableSlots = availableSlots;
	}
	public boolean isOnCall() {
		return onCall;
	}
	public void setOnCall(boolean onCall) {
		this.onCall = onCall;
	}
   
}
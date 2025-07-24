// Nurse.java (expanded)
package ehr.model.Users;


import java.util.List;

import ehr.model.utility.Department;

public non-sealed class Nurse extends EHRUser {
    private String licenseNumber;
    private int yearsOfExperience;
    private Department department;
    private boolean isHeadNurse;
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public int getYearsOfExperience() {
		return yearsOfExperience;
	}
	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public boolean isHeadNurse() {
		return isHeadNurse;
	}
	public void setHeadNurse(boolean isHeadNurse) {
		this.isHeadNurse = isHeadNurse;
	}
    
}
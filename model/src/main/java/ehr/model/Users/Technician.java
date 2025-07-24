// Technician.java (new)
package ehr.model.Users;


import java.util.List;

import ehr.model.utility.Department;

public non-sealed class Technician extends EHRUser {
    private Department department;
    private String technicianType; // Lab, Radiology, etc.
    private List<String> certifications;
    private String licenseNumber;
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getTechnicianType() {
		return technicianType;
	}
	public void setTechnicianType(String technicianType) {
		this.technicianType = technicianType;
	}
	public List<String> getCertifications() {
		return certifications;
	}
	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
    
}
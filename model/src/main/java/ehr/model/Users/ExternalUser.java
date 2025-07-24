// ExternalUser.java (expanded)
package ehr.model.Users;

import java.time.LocalDate;
import java.util.List;

public non-sealed class ExternalUser extends EHRUser {
    private String organization;
    private String role;
    private String accessLevel;
    private String contractNumber;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private List<String> allowedDepartments;
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public LocalDate getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(LocalDate contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public LocalDate getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(LocalDate contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public List<String> getAllowedDepartments() {
		return allowedDepartments;
	}
	public void setAllowedDepartments(List<String> allowedDepartments) {
		this.allowedDepartments = allowedDepartments;
	}
    
}
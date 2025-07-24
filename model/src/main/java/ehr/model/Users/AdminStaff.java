// AdminStaff.java (new)
package ehr.model.Users;

import java.util.List;

import ehr.model.utility.Department;

public non-sealed class AdminStaff extends EHRUser {
    private Department department;
    private String position;
    private List<String> permissions;
    private boolean canApproveRequests;
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public List<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	public boolean isCanApproveRequests() {
		return canApproveRequests;
	}
	public void setCanApproveRequests(boolean canApproveRequests) {
		this.canApproveRequests = canApproveRequests;
	}
    
    
}
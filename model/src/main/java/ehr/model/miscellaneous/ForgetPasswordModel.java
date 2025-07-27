package ehr.model.miscellaneous;

public class ForgetPasswordModel {
    private String ehrId;
    private String phone;
    private String verificationMethod;
	public String getEhrId() {
		return ehrId;
	}
	public void setEhrId(String ehrId) {
		this.ehrId = ehrId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getVerificationMethod() {
		return verificationMethod;
	}
	public void setVerificationMethod(String verificationMethod) {
		this.verificationMethod = verificationMethod;
	} 
    
}

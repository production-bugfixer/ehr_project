package com.ehr.authenticate.dto;

public class ResetPasswordRequest {

    private String requestId;
    private String otp;
    private String newPassword;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String requestId, String otp, String newPassword) {
        this.requestId = requestId;
        this.otp = otp;
        this.newPassword = newPassword;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "requestId='" + requestId + '\'' +
                ", otp='" + otp + '\'' +
                ", newPassword='******'" + // hide password in logs
                '}';
    }
}

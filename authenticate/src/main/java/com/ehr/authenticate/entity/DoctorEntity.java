package com.ehr.authenticate.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class DoctorEntity extends EHRUserEntity {

    private Long specializationId;
    private Integer yearsOfExperience;
    private String licenseNumber;
    private Boolean onCall;

    @ManyToOne
    @JoinColumn(name = "specializationId", insertable = false, updatable = false)
    private SpecializationEntity specialization;

	public Long getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(Long specializationId) {
		this.specializationId = specializationId;
	}

	public Integer getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Boolean getOnCall() {
		return onCall;
	}

	public void setOnCall(Boolean onCall) {
		this.onCall = onCall;
	}

	public SpecializationEntity getSpecialization() {
		return specialization;
	}

	public void setSpecialization(SpecializationEntity specialization) {
		this.specialization = specialization;
	}

    
}

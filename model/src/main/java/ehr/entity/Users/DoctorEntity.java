package ehr.entity.Users;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class DoctorEntity extends EHRUserEntity {

    @Column(nullable = false)
    private String specialization;

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}


package ehr.entity.Users;
import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class PatientEntity extends EHRUserEntity {

    @Column(nullable = false)
    private String gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth; // Ideally should be LocalDate

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

package ehr.model.allergy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Allergy {

    private Long id;

    private String allergenName;
    private String reaction;
    private String severity;
    private String notes;

    // Constructors
    public Allergy() {
    }

    public Allergy(String allergenName, String reaction, String severity, String notes) {
        this.allergenName = allergenName;
        this.reaction = reaction;
        this.severity = severity;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getAllergenName() {
        return allergenName;
    }

    public void setAllergenName(String allergenName) {
        this.allergenName = allergenName;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

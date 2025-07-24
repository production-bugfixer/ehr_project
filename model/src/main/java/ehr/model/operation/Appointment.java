package ehr.model.operation;

import ehr.model.Users.Doctor;
import ehr.model.Users.Patient;
import java.time.LocalDateTime;

public class Appointment {
 private Long id;
 private Patient patient;
 private Doctor doctor;
 private LocalDateTime appointmentDateTime;
 private LocalDateTime endDateTime;
 private String reason;
 private String status;
 private String notes;
 private Room room;
 
 // Getters and setters
}

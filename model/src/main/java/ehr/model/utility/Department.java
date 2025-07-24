package ehr.model.utility;


import java.util.List;

import ehr.model.Users.EHRUser;
import ehr.model.operation.Room;

public class Department {
 private Long id;
 private String name;
 private String code;
 private String description;
 private EHRUser headOfDepartment;
 private List<Room> rooms;
 private String contactInfo;
 
 // Getters and setters
}
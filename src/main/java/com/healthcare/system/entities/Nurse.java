package com.healthcare.system.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Nurse {
    private int id;
    private String name;
    private String password;
    private List<Complaint> complaintList;
    private HealthProvider healthProvider;
    private List<Doctor> doctorList;
    private List<Patient> patientList;
    private List<Reason> reasons;
    private String email;
    private String sessionId;


    public Nurse(){
        this.complaintList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.patientList = new ArrayList<>();
        this.reasons = new ArrayList<>();
    }

}

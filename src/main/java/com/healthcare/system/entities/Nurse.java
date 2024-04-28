package com.healthcare.system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String password;
    private List<Complaint> complaintList;
    private HealthProvider healthProvider;
    private List<Doctor> doctorList;
    private List<Patient> patientList;
    private List<Reason> reasons;
    private String email;


    public Nurse(){
        this.complaintList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.patientList = new ArrayList<>();
        this.reasons = new ArrayList<>();
    }

}

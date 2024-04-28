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
public class HealthProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String password;

    private List<Appointment> appointmentList;

    private String email;

    private List<Patient> patientList;

    private List<Doctor> doctorList;

    private List<HealthRecord> healthRecords;

    private List<Reason> reasons;

    private List<Nurse> nurseList;
    private List<Complaint> complaintList;

    public HealthProvider() {
        this.healthRecords = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.reasons = new ArrayList<>();
        this.patientList = new ArrayList<>();
        this.nurseList = new ArrayList<>();
        this.complaintList = new ArrayList<>();
    }


}

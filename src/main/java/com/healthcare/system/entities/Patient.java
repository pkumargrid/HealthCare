package com.healthcare.system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String password;

    private List<HealthProvider> healthProviderList;
    private List<Doctor> doctorList;
    private String email;

    private Nurse nurse;

    private List<HealthRecord> healthRecordList;
    private String sessionId;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointmentList;

    private List<Complaint> complaintList;


    public Patient() {
        this.healthProviderList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.complaintList = new ArrayList<>();
    }


}

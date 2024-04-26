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
    private Integer id;
    private String name;
    private String password;

    @ManyToMany(mappedBy = "patientList")
    private List<HealthProvider> healthProviderList;

    @ManyToMany(mappedBy = "patientList")
    private List<Doctor> doctorList;
    private String email;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    @OneToMany(mappedBy = "patient")
    private List<HealthRecord> healthRecordList;
    private String sessionId;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointmentList;

    @OneToMany
    @JoinColumn(name = "patient_id")
    private List<Complaint> complaintList;


    public Patient() {
        this.healthProviderList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.complaintList = new ArrayList<>();
    }


}

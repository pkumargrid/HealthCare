package com.healthcare.system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String password;

    private String email;

    private List<Patient> patientList;

    private HealthProvider healthProvider;

    private List<Complaint> complaintList;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointmentList;

    private List<Reason> reasons;

    public Doctor() {
        this.patientList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.reasons = new ArrayList<>();
        this.complaintList = new ArrayList<>();
    }

}

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
    private Integer id;

    private String name;

    private String password;

    private String email;

    @ManyToMany
    @JoinTable(name = "patient_doctor", joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patientList;

    @ManyToOne
    @JoinColumn(name = "health_care_provider_id")
    private HealthProvider healthProvider;


    @OneToMany
    @JoinColumn(name = "complaint_id")
    private List<Complaint> complaintList;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointmentList;

    @OneToMany
    @JoinColumn(name = "reason_id")
    private List<Reason> reasons;

    @OneToMany(mappedBy = "doctor")
    private List<HealthRecord> healthRecords;

    @ManyToMany(mappedBy = "doctorList")
    private List<Nurse> nurseList;

    public Doctor() {
        this.patientList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.reasons = new ArrayList<>();
        this.complaintList = new ArrayList<>();
        this.healthRecords = new ArrayList<>();
    }

}

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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinTable(name = "patient_doctor", joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patientList;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "health_care_provider_id")
    private HealthProvider healthProvider;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    private List<Complaint> complaintList;

    @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY)
    private List<Appointment> appointmentList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id")
    private List<Reason> reasons;

    @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY)
    private List<HealthRecord> healthRecords;

    @ManyToMany(mappedBy = "doctorList",fetch = FetchType.LAZY)
    private List<Nurse> nurseList;

    public Doctor() {
        this.patientList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.reasons = new ArrayList<>();
        this.complaintList = new ArrayList<>();
        this.healthRecords = new ArrayList<>();
    }

}

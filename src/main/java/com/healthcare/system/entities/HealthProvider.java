package com.healthcare.system.entities;


import jakarta.persistence.*;
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
    private Integer id;

    private String name;

    private String password;

    @OneToMany
    @JoinColumn(name = "health_care_provider_id")
    private List<Appointment> appointmentList;

    private String email;

    @ManyToMany
    @JoinTable(name = "patient_heath_care_provider",
                joinColumns = @JoinColumn(name = "health_care_provider_id"), inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patientList;

    @OneToMany(mappedBy = "healthProvider")
    private List<Doctor> doctorList;

    @OneToMany(mappedBy = "healthProvider")
    private List<HealthRecord> healthRecords;

    @OneToMany
    @JoinColumn(name = "health_care_provider_id")
    private List<Reason> reasons;

    @OneToMany(mappedBy = "healthProvider")
    private List<Nurse> nurseList;

    @OneToMany
    @JoinColumn(name = "health_care_provider_id")
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

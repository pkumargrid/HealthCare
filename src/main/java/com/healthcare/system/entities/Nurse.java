package com.healthcare.system.entities;

import jakarta.persistence.*;
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
    private Integer id;
    private String name;
    private String password;

    @OneToMany
    @JoinColumn(name = "nurse_id")
    private List<Complaint> complaintList;

    @ManyToOne
    @JoinColumn(name ="health_care_provider_id")
    private HealthProvider healthProvider;

    @ManyToMany
    @JoinTable(name = "nurse_doctor", joinColumns = @JoinColumn(name = "nurse_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private List<Doctor> doctorList;

    @OneToMany(mappedBy = "nurse")
    private List<Patient> patientList;
    @OneToMany
    @JoinColumn(name = "nurse_id")
    private List<Reason> reasons;
    private String email;

    public Nurse(){
        this.complaintList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.patientList = new ArrayList<>();
        this.reasons = new ArrayList<>();
    }

}

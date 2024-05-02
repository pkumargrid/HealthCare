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

    private String role;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "nurse_id")
    private List<Complaint> complaintList;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name ="health_care_provider_id")
    private HealthProvider healthProvider;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "nurse_doctor", joinColumns = @JoinColumn(name = "nurse_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private List<Doctor> doctorList;

    @OneToMany(mappedBy = "nurse", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Patient> patientList;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
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

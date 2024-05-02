package com.healthcare.system.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String role;


    @ManyToMany(mappedBy = "patientList", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonBackReference
    private List<HealthProvider> healthProviderList;

    @ManyToMany(mappedBy = "patientList", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonBackReference
    private List<Doctor> doctorList;

    private String email;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "nurse_id")
    @JsonManagedReference
    private Nurse nurse;


    @OneToMany(mappedBy = "patient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonManagedReference
    private List<HealthRecord> healthRecordList;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Appointment> appointmentList;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonManagedReference
    private List<Complaint> complaintList;


    public Patient() {
        this.healthProviderList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.complaintList = new ArrayList<>();
    }


}

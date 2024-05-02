package com.healthcare.system.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
public class HealthProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private String role;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "health_care_provider_id")
    @JsonManagedReference
    private List<Appointment> appointmentList;

    private String email;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonManagedReference
    @JoinTable(name = "patient_heath_care_provider",
                joinColumns = @JoinColumn(name = "health_care_provider_id"), inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patientList;

    @OneToMany(mappedBy = "healthProvider", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonManagedReference
    private List<Doctor> doctorList;

    @OneToMany(mappedBy = "healthProvider", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonManagedReference
    private List<HealthRecord> healthRecords;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonManagedReference
    @JoinColumn(name = "health_care_provider_id")
    private List<Reason> reasons;


    @OneToMany(mappedBy = "healthProvider", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonManagedReference
    private List<Nurse> nurseList;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "health_care_provider_id")
    @JsonManagedReference
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

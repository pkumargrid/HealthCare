package com.healthcare.system.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class Doctor {

    private int id;

    private String name;

    private String password;

    private String email;

    private List<Patient> patientList;

    private HealthProvider healthProvider;

    private String sessionId;

    private List<Complaint> complaintList;

    private List<Appointment> appointmentList;

    private List<Reason> reasons;

    public Doctor() {
        this.patientList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.reasons = new ArrayList<>();
        this.complaintList = new ArrayList<>();
    }

}

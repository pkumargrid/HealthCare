package com.healthcare.system.entities;

import java.util.ArrayList;
import java.util.List;

public class Nurse {
    private int id;
    private String name;
    private String password;
    private List<Complaint> complaintList;
    private HealthProvider healthProvider;
    private List<Doctor> doctorList;
    private List<Patient> patientList;
    private List<Reason> reasons;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Nurse(){
        this.complaintList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
        this.patientList = new ArrayList<>();
        this.reasons = new ArrayList<>();
    }

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Complaint> getComplaintList() {
        return complaintList;
    }

    public void setComplaintList(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    public HealthProvider getHealthProvider() {
        return healthProvider;
    }

    public void setHealthProvider(HealthProvider healthProvider) {
        this.healthProvider = healthProvider;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }
}

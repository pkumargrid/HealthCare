package com.healthcare.system.entities;

import java.util.ArrayList;
import java.util.List;


public class Doctor {

    private int id;

    private String name;

    private String password;

    private String email;

    private List<Patient> patientList;

    private HealthProvider healthProvider;

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Doctor() {
        this.patientList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.reasons = new ArrayList<>();
        this.complaintList = new ArrayList<>();
    }

    private List<Complaint> complaintList;

    private List<Appointment> appointmentList;

    private List<Reason> reasons;

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public int getId() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public HealthProvider getHealthProvider() {
        return healthProvider;
    }

    public void setHealthProvider(HealthProvider healthProvider) {
        this.healthProvider = healthProvider;
    }

    public List<Complaint> getComplaintList() {
        return complaintList;
    }

    public void setComplaintList(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

}

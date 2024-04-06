package com.healthcare.system.entities;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int id;
    private String name;
    private String password;

    private List<HealthProvider> healthProvidersList;
    private List<Doctor> doctorList;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Nurse nurse;

    private List<HealthRecord> healthRecordList;
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Patient() {
        this.healthProvidersList = new ArrayList<>();
        this.doctorList = new ArrayList<>();
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

    public List<HealthProvider> getHealthProvidersList() {
        return healthProvidersList;
    }

    public void setHealthProvidersList(List<HealthProvider> healthProvidersList) {
        this.healthProvidersList = healthProvidersList;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public List<HealthRecord> getHealthRecordList() {
        return healthRecordList;
    }

    public void setHealthRecordList(List<HealthRecord> healthRecordList) {
        this.healthRecordList = healthRecordList;
    }

}

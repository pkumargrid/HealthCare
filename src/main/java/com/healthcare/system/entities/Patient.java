package com.healthcare.system.entities;

import java.util.List;

public class Patient {
<<<<<<< HEAD
    int id;
    String name;
    String password;
    List<HealthProvider> healthProvidersList;
    List<Doctor> doctorList;
=======
    private int id;
    private String name;
    private String password;
>>>>>>> health_provider

    private Nurse nurse;
    private List<HealthProviders> healthProvidersList;
    private List<Doctor> doctorList;

    private List<HealthRecord> healthRecordList;

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

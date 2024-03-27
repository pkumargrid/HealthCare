package com.healthcare.system.entities;

import java.util.List;

public class Patient {
    Integer id;
    String name;
    String password;
    List<HealthProviders> healthProvidersList;
    List<Doctor> doctorList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<HealthProviders> getHealthProvidersList() {
        return healthProvidersList;
    }

    public void setHealthProvidersList(List<HealthProviders> healthProvidersList) {
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

    Nurse nurse;
    List<HealthRecord> healthRecordList;
}

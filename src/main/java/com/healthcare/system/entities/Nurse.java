package com.healthcare.system.entities;

import java.util.List;

public class Nurse {
    int id;
    String name;
    String password;
    List<Complaint> complaintList;
    List<HealthProvider> healthProviderList;
    List<Doctor> doctorList;
    List<Patient> patientList;

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

    public List<HealthProvider> getHealthProvidersList() {
        return healthProviderList;
    }

    public void setHealthProvidersList(List<HealthProvider> healthProviderList) {
        this.healthProviderList = healthProviderList;
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

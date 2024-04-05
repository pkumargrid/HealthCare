package com.healthcare.system.entities;


import java.util.List;

public class HealthProviders {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<Patient> patientList;

    private List<Doctor> doctorList;

    private List<HealthRecord> healthRecords;

    private List<Reason> reasons;

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public List<HealthRecord> getHealthRecords() {
        return healthRecords;
    }

    public void setHealthRecords(List<HealthRecord> healthRecords) {
        this.healthRecords = healthRecords;
    }

}

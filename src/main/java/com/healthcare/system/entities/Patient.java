package com.healthcare.system.entities;

import java.util.List;

public class Patient {

    private int id;
    private String name;
    private String password;
    private Nurse nurse;
    private List<HealthProviders> healthProvidersList;
    private List<Doctor> doctorList;

    private List<HealthRecord> healthRecordList;

    private List<Appointment> appointmentList;

    private List<Complaint> complaintList;

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

}

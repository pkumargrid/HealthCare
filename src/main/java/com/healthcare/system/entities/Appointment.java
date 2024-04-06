package com.healthcare.system.entities;

import java.time.LocalDateTime;


public class Appointment {

    private boolean status;
    private  int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Doctor doctor;

    private Patient patient;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}

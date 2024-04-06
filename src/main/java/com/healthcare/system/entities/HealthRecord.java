package com.healthcare.system.entities;

public class HealthRecord {
    private HealthProvider healthProvider;
    private Doctor doctor;
    private Patient patient;
    private Prescription prescription;
    private Report report;

    public HealthProvider getHealthProvider() {
        return healthProvider;
    }

    public void setHealthProvider(HealthProvider healthProvider) {
        this.healthProvider = healthProvider;
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

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}

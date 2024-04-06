package com.healthcare.system.services;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.AppointmentTimeException;

import java.util.List;

public interface PatientService {
    Patient findById(int id);
    List<Patient> findAll();
    void savePatient(Patient patient);
    void updatePatient(Patient patient);
    void deletePatientById(int id);
    void bookAppointments(Appointment appointment) throws AppointmentTimeException;
    void createComplaints(Complaint complaint, String type, int id);
    void login();
}

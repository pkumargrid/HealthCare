package com.healthcare.system.services;

import com.healthcare.system.dto.AppointmentDTO;
import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;

import java.util.List;

public interface PatientService {
    Patient findById(int id) throws WrongCredentials;
    List<Patient> findAll();
    void savePatient(Patient patient) throws ValidationException, WrongCredentials;
    void updatePatient(Patient patient) throws ValidationException, WrongCredentials;
    void deletePatientById(int id) throws WrongCredentials;
    void bookAppointments(AppointmentDTO appointmentDTO) throws AppointmentTimeException, WrongCredentials;

    void createComplaint(Complaint complaint, String type, int id) throws WrongCredentials;

    HealthRecord accessPatientRecord(Patient patient);

    void register(PatientDTO patientDTO) throws ValidationException, WrongCredentials;
    List<HealthProvider> getHealthProviders(int patientId) throws WrongCredentials;
    List<Doctor> getDoctorList(int patientId) throws WrongCredentials;
    List<HealthRecord> getHealthRecordList(int patientId) throws WrongCredentials;

}

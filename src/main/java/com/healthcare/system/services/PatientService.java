package com.healthcare.system.services;

import com.healthcare.system.controllers.dto.PatientDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;

import java.util.List;

public interface PatientService {
    Patient findById(int id) throws WrongCredentials;
    List<Patient> findAll();
    void savePatient(Patient patient) throws ValidationException;
    void updatePatient(Patient patient) throws ValidationException;
    void deletePatientById(int id) throws WrongCredentials;
    void bookAppointments(Appointment appointment) throws AppointmentTimeException;

    void createComplaint(Complaint complaint, String type, int id);

    void login(Patient patient) throws ValidationException, AlreadyLoggedInException;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(PatientDTO patientDTO) throws ValidationException;
    List<HealthProvider> getHealthProviders(int patientId) throws WrongCredentials;
    List<Doctor> getDoctorList(int patientId) throws WrongCredentials;
    List<HealthRecord> getHealthRecordList(int patientId) throws WrongCredentials;

}

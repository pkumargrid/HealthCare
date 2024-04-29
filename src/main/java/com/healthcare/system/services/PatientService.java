package com.healthcare.system.services;

import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;

import java.rmi.ServerException;
import java.util.List;

public interface PatientService {
    Patient findById(int id) throws WrongCredentials, ServerException;
    List<Patient> findAll() throws ServerException, WrongCredentials;
    void savePatient(Patient patient) throws ValidationException, WrongCredentials, ServerException;
    void updatePatient(Patient patient) throws ValidationException, WrongCredentials, ServerException;
    void deletePatientById(int id) throws WrongCredentials, ServerException;
    void bookAppointments(Appointment appointment) throws AppointmentTimeException, WrongCredentials, ServerException;

    void createComplaint(Complaint complaint, String type, int id) throws WrongCredentials, ServerException;

    void login(PatientDTO patient) throws ValidationException, AlreadyLoggedInException, ServerException, WrongCredentials;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(PatientDTO patientDTO) throws ValidationException, WrongCredentials, ServerException;
    List<HealthProvider> getHealthProviders(int patientId) throws WrongCredentials, ServerException;
    List<Doctor> getDoctorList(int patientId) throws WrongCredentials, ServerException;
    List<HealthRecord> getHealthRecordList(int patientId) throws WrongCredentials, ServerException;

}

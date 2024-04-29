package com.healthcare.system.services;

import com.healthcare.system.dto.DoctorDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;

import java.rmi.ServerException;
import java.util.List;
public interface DoctorService {

    void register(DoctorDTO doctor) throws ValidationException, WrongCredentials, ServerException;

    Doctor getById(int id) throws WrongCredentials, ServerException;
    void deleteById(int id) throws WrongCredentials, ServerException;

    List<Doctor> getByName(String name) throws ServerException, WrongCredentials;

    void update(Doctor doctor) throws ValidationException, WrongCredentials, ServerException;

    List<Doctor> findAll() throws ServerException, WrongCredentials;

    void assignNurse(int id, Patient patient) throws WrongCredentials, ServerException;

    void confirmAppointment(int id) throws ServerException, WrongCredentials;

    void notifyReasonForComplaint(Reason reason) throws ReasonTypeException, ResourceNotFoundException, WrongCredentials, ServerException;

    void login(DoctorDTO doctorDTO) throws ValidationException, AlreadyLoggedInException, ServerException, WrongCredentials;

    void save(Doctor doctor) throws ValidationException, WrongCredentials, ServerException;

    void logout(String sessionId) throws AlreadyLoggedOutException;

    List<Appointment> getAppointments(int doctorId) throws WrongCredentials, ServerException;

    List<Complaint> getComplaints(int doctorId) throws WrongCredentials, ServerException;

    List<Reason> getReasons(int doctorId) throws WrongCredentials, ServerException;

    List<Patient> getPatients(int doctorId) throws WrongCredentials, ServerException;

    void prescribePrescription(int doctorId, int patientId, String prescription) throws ValidationException, WrongCredentials, ServerException;

    void updatePrescription(int healthRecordId, String prescription) throws ValidationException, WrongCredentials, ServerException;
}

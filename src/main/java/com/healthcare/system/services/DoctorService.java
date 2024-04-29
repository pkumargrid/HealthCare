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

    List<Doctor> getByName(String name);

    void update(Doctor doctor) throws ValidationException, WrongCredentials, ServerException;

    List<Doctor> findAll() throws ServerException, WrongCredentials;

    void assignNurse(int id, Patient patient) throws WrongCredentials, ServerException;

    void confirmAppointment(int id);

    void notifyReasonForComplaint(Reason reason) throws ReasonTypeException, ResourceNotFoundException, WrongCredentials, ServerException;

    void login(Doctor doctor) throws ValidationException, AlreadyLoggedInException;

    void save(Doctor doctor) throws ValidationException, WrongCredentials;

    void logout(String sessionId) throws AlreadyLoggedOutException;

    List<Appointment> getAppointments(int doctorId) throws WrongCredentials;

    List<Complaint> getComplaints(int doctorId) throws WrongCredentials;

    List<Reason> getReasons(int doctorId) throws WrongCredentials;

    List<Patient> getPatients(int doctorId) throws WrongCredentials;

    void prescribePrescription(int doctorId, int patientId, Prescription prescription) throws ValidationException, WrongCredentials;

    void updatePrescription(int healthRecordId, Prescription prescription) throws ValidationException, WrongCredentials;
}

package com.healthcare.system.services;

import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;

import java.util.List;
public interface DoctorService {

    void register(Doctor doctor) throws ValidationException;

    Doctor getById(int id) throws WrongCredentials;
    Doctor deleteById(int id) throws WrongCredentials;

    List<Doctor> getByName(String name);

    void update(Doctor doctor) throws ValidationException;

    List<Doctor> findAll();

    void assignNurse(int id, Patient patient);

    void confirmAppointment(int id);

    void notifyReasonForComplaint(Reason reason) throws ReasonTypeException, ResourceNotFoundException;

    void login(Doctor doctor) throws ValidationException, AlreadyLoggedInException;

    void save(Doctor doctor) throws ValidationException;

    void logout(String sessionId) throws AlreadyLoggedOutException;

    List<Appointment> getAppointments(int doctorId) throws WrongCredentials;

    List<Complaint> getComplaints(int doctorId) throws WrongCredentials;

    List<Reason> getReasons(int doctorId) throws WrongCredentials;

    List<Patient> getPatients(int doctorId) throws WrongCredentials;

}

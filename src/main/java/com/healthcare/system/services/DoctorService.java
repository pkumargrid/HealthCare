package com.healthcare.system.services;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.*;

import java.util.List;
public interface DoctorService {

    void register(Doctor doctor) throws ValidationException;

    Doctor getById(int id);
    Doctor deleteById(int id);

    List<Doctor> getByName(String name);

    void update(Doctor doctor);

    List<Doctor> findAll();

    void assignNurse(int id, Patient patient);

    void confirmAppointment(int id);

    void notifyReasonForComplaint(Reason reason) throws ReasonTypeException, ResourceNotFoundException;

    void login(Doctor doctor) throws ValidationException, AlreadyLoggedInException;

    void save(Doctor doctor);

    void logout(String sessionId) throws AlreadyLoggedOutException;
}

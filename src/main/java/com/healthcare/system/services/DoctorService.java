package com.healthcare.system.services;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.ReasonTypeException;
import com.healthcare.system.exceptions.ResourceNotFoundException;

import java.util.List;
public interface DoctorService {
    void save(Doctor doctor);

    Doctor getById(int id);
    Doctor deleteById(int id);

    List<Doctor> getByName(String name);

    void update(Doctor doctor);

    List<Doctor> findAll();

    void assignNurse(int id, Patient patient);

    void confirmAppointment(int id);

    void notifyReasonForComplaint(Reason reason) throws ReasonTypeException, ResourceNotFoundException;
}

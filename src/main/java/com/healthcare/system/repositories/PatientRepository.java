package com.healthcare.system.repositories;

import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface PatientRepository {
    Patient findById(int id);
    List<Patient> findAll();
    void save(Patient patient) throws WrongCredentials;
    void update(Patient patient) throws WrongCredentials;
    void deleteById(int id) throws WrongCredentials;

    List<Patient> findPatientByDoctorId(int id);
}

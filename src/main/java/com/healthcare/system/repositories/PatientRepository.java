package com.healthcare.system.repositories;

import com.healthcare.system.entities.Patient;

import java.util.List;

public interface PatientRepository {
    Patient findById(int id);
    List<Patient> findAll();
    void save(Patient patient);
    void update(Patient patient);
    void deleteById(int id);
}

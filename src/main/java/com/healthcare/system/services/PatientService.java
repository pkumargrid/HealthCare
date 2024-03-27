package com.healthcare.system.services;

import com.healthcare.system.entities.Patient;
import java.util.List;

public interface PatientService {
    Patient findById(int id);
    List<Patient> findAll();
    void savePatient(Patient patient);
    void updatePatient(Patient patient);
    void deletePatientById(int id);
}

package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface NurseRepository {
    Nurse findById(int id);
    List<Nurse> findAll();
    void saveNurse(Nurse nurse) throws WrongCredentials;
    void updateNurse(Nurse nurse) throws WrongCredentials;
    void deleteNurseById(int id) throws WrongCredentials;
    HealthRecord accessPatientRecord(Patient patient);
}

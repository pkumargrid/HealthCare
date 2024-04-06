package com.healthcare.system.services;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.entities.Patient;

import java.util.List;

public interface NurseService {
    Nurse findById(int id);
    List<Nurse> findAll();
    void saveNurse(Nurse nurse);
    void updateNurse(Nurse nurse);
    void deleteNurseById(int id);
    HealthRecord accessPatientRecord(Patient patient);

}

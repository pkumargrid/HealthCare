package com.healthcare.system.services;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.entities.Patient;

import java.util.List;

public interface NurseService {
    Nurse findById(int id);
    List<Nurse> findAll();
    void saveNurse(Nurse nurse);
    void updateNurse(Nurse nurse);
    void deleteNurseById(int id);

    void login(Nurse nurse) throws ValidationException, AlreadyLoggedInException;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(Nurse nurse) throws ValidationException, AlreadyLoggedInException;
    HealthRecord accessPatientRecord(Patient patient);
}

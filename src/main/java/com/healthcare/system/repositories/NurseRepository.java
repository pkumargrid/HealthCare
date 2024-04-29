package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface NurseRepository {
    Nurse findById(int id) throws ServerException, WrongCredentials;
    List<Nurse> findAll() throws ServerException, WrongCredentials;
    void saveNurse(Nurse nurse) throws WrongCredentials, ServerException;
    void updateNurse(Nurse nurse) throws WrongCredentials, ServerException;
    void deleteNurseById(int id) throws WrongCredentials, ServerException;
    HealthRecord accessPatientRecord(Integer patientId);

    List<Nurse> findByHealthProviderId(int id) throws ServerException, WrongCredentials;
}

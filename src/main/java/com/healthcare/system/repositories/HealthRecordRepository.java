package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface HealthRecordRepository {
    void save(HealthRecord healthRecord) throws WrongCredentials, ServerException;

    HealthRecord getById(int id) throws WrongCredentials,ServerException;

    void deleteById(int id) throws WrongCredentials,ServerException;

    void update(HealthRecord healthRecord) throws WrongCredentials,ServerException;


    List<HealthRecord> findByPatientId(Integer patientId) throws WrongCredentials,ServerException;

    List<HealthRecord> findAll() throws WrongCredentials,ServerException;

    List<HealthRecord> findByHealthProviderId();
}

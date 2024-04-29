package com.healthcare.system.services;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface HealthRecordService {

    void save(HealthRecord healthRecord) throws ValidationException, WrongCredentials, ServerException;

    HealthRecord getById(int id) throws WrongCredentials, ServerException;

    void deleteById(int id) throws WrongCredentials, ServerException;

    void update(HealthRecord healthRecord) throws ValidationException, WrongCredentials, ServerException;

    List<HealthRecord> findAll() throws ServerException, WrongCredentials;
}

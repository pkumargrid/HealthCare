package com.healthcare.system.services;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface HealthRecordService {

    void save(HealthRecord healthRecord) throws ValidationException, WrongCredentials;

    HealthRecord getById(int id) throws WrongCredentials;

    void deleteById(int id) throws WrongCredentials;

    void update(HealthRecord healthRecord) throws ValidationException, WrongCredentials;

    List<HealthRecord> findAll();
}

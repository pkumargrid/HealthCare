package com.healthcare.system.services;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface HealthRecordService {

    void save(HealthRecord healthRecord) throws ValidationException;

    HealthRecord getById(int id) throws WrongCredentials;

    HealthRecord deleteById(int id) throws WrongCredentials;

    void update(HealthRecord healthRecord) throws ValidationException;

    List<HealthRecord> findAll();
}

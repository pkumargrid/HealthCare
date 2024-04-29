package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface HealthRecordRepository {
    void save(HealthRecord healthRecord) throws WrongCredentials;

    HealthRecord getById(int id);

    HealthRecord deleteById(int id) throws WrongCredentials;

    void update(HealthRecord healthRecord) throws WrongCredentials;

    List<HealthRecord> findAll();

    HealthRecord findByPatientId(Integer patientId);

    List<HealthRecord> findByHealthProviderById(int id);
}

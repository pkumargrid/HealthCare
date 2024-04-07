package com.healthcare.system.services;

import com.healthcare.system.entities.HealthRecord;

import java.util.List;

public interface HealthRecordService {

    void save(HealthRecord healthRecord);

    HealthRecord getById(int id);

    HealthRecord deleteById(int id);

    void update(HealthRecord healthRecord);

    List<HealthRecord> findAll();
}

package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.entities.HealthRecord;

import java.util.List;

public interface HealthRecordRepository {
    void save(HealthRecord healthRecord);

    HealthRecord getById(int id);

    HealthRecord deleteById(int id);

    void update(HealthRecord healthRecord);

    List<HealthRecord> findAll();
}

package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.repositories.HealthRecordRepository;
import com.healthcare.system.services.HealthRecordService;

import java.util.List;

public class HealthRecordServiceImpl implements HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;

    public HealthRecordServiceImpl(HealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }
    @Override
    public void save(HealthRecord healthRecord) {
        healthRecordRepository.save(healthRecord);

    }

    @Override
    public HealthRecord getById(int id) {
        return healthRecordRepository.getById(id);
    }

    @Override
    public HealthRecord deleteById(int id) {
        return healthRecordRepository.deleteById(id);
    }

    @Override
    public void update(HealthRecord healthRecord) {
        healthRecordRepository.update(healthRecord);
    }

    @Override
    public List<HealthRecord> findAll() {
        return healthRecordRepository.findAll();
    }
}

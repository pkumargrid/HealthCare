package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.HealthRecordRepository;
import com.healthcare.system.services.HealthRecordService;

import java.util.List;

import static com.healthcare.system.util.Verification.verifyCredentials;

public class HealthRecordServiceImpl implements HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;

    public HealthRecordServiceImpl(HealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }
    @Override
    public void save(HealthRecord healthRecord) throws ValidationException {
        verifyCredentials(HealthRecord.class,healthRecord);
        healthRecordRepository.save(healthRecord);

    }

    @Override
    public HealthRecord getById(int id) throws WrongCredentials {
        if(healthRecordRepository.getById(id) == null) {
            throw new WrongCredentials("HealthRecord with id: " + id + " does not exist");
        }
        return healthRecordRepository.getById(id);
    }

    @Override
    public HealthRecord deleteById(int id) throws WrongCredentials {
        if(healthRecordRepository.getById(id) == null) {
            throw new WrongCredentials("HealthRecord with id: " + id + " does not exist");
        }
        return healthRecordRepository.deleteById(id);
    }

    @Override
    public void update(HealthRecord healthRecord) throws ValidationException {
        verifyCredentials(HealthRecord.class,healthRecord);
        healthRecordRepository.update(healthRecord);
    }

    @Override
    public List<HealthRecord> findAll() {
        return healthRecordRepository.findAll();
    }
}

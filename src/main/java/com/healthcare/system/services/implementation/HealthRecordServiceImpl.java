package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.HealthRecordRepository;
import com.healthcare.system.services.HealthRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.healthcare.system.util.Verification.verifyCredentials;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;

    public HealthRecordServiceImpl(HealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }
    @Override
    public void save(HealthRecord healthRecord) throws ValidationException, WrongCredentials {
        verifyCredentials(HealthRecord.class, healthRecord);
        healthRecordRepository.save(healthRecord);

    }

    @Override
    public HealthRecord getById(int id) throws WrongCredentials {
        if (healthRecordRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("HealthRecord with id: " + id + " does not exist");
        }
        return healthRecordRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        if (healthRecordRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("HealthRecord with id: " + id + " does not exist");
        }
        healthRecordRepository.deleteById(id);
    }

    @Override
    public void update(HealthRecord healthRecord) throws ValidationException, WrongCredentials {
        verifyCredentials(HealthRecord.class, healthRecord);
        healthRecordRepository.save(healthRecord);
    }

    @Override
    public List<HealthRecord> findAll() {
        return healthRecordRepository.findAll();
    }
}

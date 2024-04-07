package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.repositories.HealthRecordRepository;

import java.util.ArrayList;
import java.util.List;

public class HealthRecordRepositoryImpl implements HealthRecordRepository {

    List<HealthRecord> healthRecordList;


    public HealthRecordRepositoryImpl() {
        healthRecordList = new ArrayList<>();
    }

    @Override
    public void save(HealthRecord healthRecord) {
        healthRecordList.add(healthRecord);
    }

    @Override
    public HealthRecord getById(int id) {
        return healthRecordList.stream().filter(healthRecord -> healthRecord.getId() == id).findFirst().get();
    }


    @Override
    public HealthRecord deleteById(int id) {
        HealthRecord healthRecord = healthRecordList.stream().filter(h -> h.getId() == id).findFirst().get();
        healthRecordList.remove(healthRecord);
        return healthRecord;
    }

    @Override
    public void update(HealthRecord healthRecord) {
        HealthRecord prevHealthRecord = healthRecordList.stream().filter(h -> h.getId() == healthRecord.getId()).findFirst().get();
        prevHealthRecord.setHealthProvider(healthRecord.getHealthProvider());
        prevHealthRecord.setDoctor(healthRecord.getDoctor());
        prevHealthRecord.setPatient(healthRecord.getPatient());
        prevHealthRecord.setPrescription(healthRecord.getPrescription());
        prevHealthRecord.setReport(healthRecord.getReport());
    }

    @Override
    public List<HealthRecord> findAll() {
        return healthRecordList.stream().toList();
    }

}

package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.HealthRecordRepository;

import java.util.ArrayList;
import java.util.List;

public class HealthRecordRepositoryImpl implements HealthRecordRepository {

    List<HealthRecord> healthRecordList;


    public HealthRecordRepositoryImpl() {
        healthRecordList = new ArrayList<>();
    }

    @Override
    public void save(HealthRecord healthRecord) throws WrongCredentials {
        if(healthRecordList.contains(healthRecord)) {
            update(healthRecord);
            return;
        }
        healthRecordList.add(healthRecord);
    }

    @Override
    public HealthRecord getById(int id) {
        return healthRecordList.stream().filter(healthRecord -> healthRecord.getId() == id).findFirst().orElseGet(()-> null);
    }


    @Override
    public HealthRecord deleteById(int id) throws WrongCredentials {
        HealthRecord healthRecord = healthRecordList.stream().filter(h -> h.getId() == id).findFirst().orElseGet(()-> null);
        if(healthRecord == null) {
            throw new WrongCredentials("HealthRecord with id " + id + " does not exist");
        }
        healthRecordList.remove(healthRecord);
        return healthRecord;
    }

    @Override
    public void update(HealthRecord healthRecord) throws WrongCredentials {
        HealthRecord prevHealthRecord = healthRecordList.stream().filter(h -> h.getId() == healthRecord.getId()).findFirst().orElseGet(()-> null);
        if(prevHealthRecord == null) {
            throw new WrongCredentials("HealthRecord with id " + healthRecord.getId() + " does not exist");
        }
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

    @Override
    public HealthRecord findByPatientId(Integer patientId) {
        return null;
    }

}

package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthProviders;
import com.healthcare.system.repositories.HealthProviderRepository;

import java.util.ArrayList;
import java.util.List;

public class HealthProviderRepositoryImpl implements HealthProviderRepository {

    List<HealthProviders> healthProvidersList;


    public HealthProviderRepositoryImpl() {
        healthProvidersList = new ArrayList<>();
    }

    @Override
    public void save(HealthProviders healthProviders) {
        healthProvidersList.add(healthProviders);
    }

    @Override
    public HealthProviders getById(int id) {
        return healthProvidersList.stream().filter(healthProvider -> healthProvider.getId() == id).findFirst().get();
    }

    @Override
    public HealthProviders deleteById(int id) {
        HealthProviders healthProviders = healthProvidersList.stream().filter(h -> h.getId() == id).findFirst().get();
        healthProvidersList.remove(healthProviders);
        return healthProviders;
    }

    @Override
    public void updateById(int id, HealthProviders healthProviders) {
        HealthProviders prevHealthProviders = healthProvidersList.stream().filter(h -> h.getId() == id).findFirst().get();
        prevHealthProviders.setHealthRecords(healthProviders.getHealthRecords());
        prevHealthProviders.setDoctorList(healthProviders.getDoctorList());
        prevHealthProviders.setReasons(healthProviders.getReasons());
        prevHealthProviders.setPatientList(healthProviders.getPatientList());
    }

    @Override
    public List<HealthProviders> findAll() {
        return healthProvidersList.stream().toList();
    }

    @Override
    public HealthProviders getByName(String name) {
        return null;
    }
}

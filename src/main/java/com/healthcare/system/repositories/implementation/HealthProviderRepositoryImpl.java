package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.repositories.HealthProviderRepository;

import java.util.ArrayList;
import java.util.List;

public class HealthProviderRepositoryImpl implements HealthProviderRepository {

    List<HealthProvider> healthProviderList;


    public HealthProviderRepositoryImpl() {
        healthProviderList = new ArrayList<>();
    }

    @Override
    public void save(HealthProvider healthProvider) {
        healthProviderList.add(healthProvider);
    }

    @Override
    public HealthProvider getById(int id) {
        return healthProviderList.stream().filter(healthProvider -> healthProvider.getId() == id).findFirst().get();
    }


    @Override
    public HealthProvider deleteById(int id) {
        HealthProvider healthProvider = healthProviderList.stream().filter(h -> h.getId() == id).findFirst().get();
        healthProviderList.remove(healthProvider);
        return healthProvider;
    }

    @Override
    public void update(HealthProvider healthProvider) {
        HealthProvider prevHealthProvider = healthProviderList.stream().filter(h -> h.getId() == healthProvider.getId()).findFirst().get();
        prevHealthProvider.setHealthRecords(healthProvider.getHealthRecords());
        prevHealthProvider.setDoctorList(healthProvider.getDoctorList());
        prevHealthProvider.setReasons(healthProvider.getReasons());
        prevHealthProvider.setPatientList(healthProvider.getPatientList());
    }

    @Override
    public List<HealthProvider> findAll() {
        return healthProviderList.stream().toList();
    }

    @Override
    public List<HealthProvider> getByName(String name) {
        return healthProviderList.stream().filter(h -> h.getName().equals(name)).toList();
    }
}

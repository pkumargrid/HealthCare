package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.repositories.HealthProviderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class HealthProviderRepositoryImpl implements HealthProviderRepository {

    List<HealthProvider> healthProviderList;


    public HealthProviderRepositoryImpl() {
        healthProviderList = new ArrayList<>();
    }

    @Override
    public void save(HealthProvider healthProvider) {
        healthProvider.add(healthProvider);
    }

    @Override
    public HealthProvider getById(int id) {
        return healthProviderList.stream().filter(healthProvider -> healthProvider.getId() == id).findFirst().get();
    }

    @Override
    public HealthProvider deleteById(int id) {
        HealthProvider healthProvider = healthProviderList.stream().filter(h -> h.getId() == id).findFirst();
        healthProviderList.remove(healthProvider);
        return healthProvider;
    }

    @Override
    public void updateById(int id, HealthProvider healthProvider) {
        HealthProvider prevHealthProvider = healthProviderList.stream().filter(h -> h.getId() == id).findFirst();
        prevHealthProvider.setHealthRecords(healthProvider.getHealthRecords());
        prevHealthProvider.setDoctorList(healthProvider.getDoctorList());
        prevHealthProvider.setReasons(healthProvider.getDoctorList());
        prevHealthProvider.setPatientList(healthProvider.getPatientList());
    }

    @Override
    public List<HealthProvider> findAll() {
        return healthProviderList.stream().toList();
    }

    @Override
    public HealthProvider getByName(String name) {
        return null;
    }
}

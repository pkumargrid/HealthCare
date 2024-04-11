package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.HealthProviderRepository;

import java.util.ArrayList;
import java.util.List;

public class HealthProviderRepositoryImpl implements HealthProviderRepository {

    List<HealthProvider> healthProviderList;


    public HealthProviderRepositoryImpl() {
        healthProviderList = new ArrayList<>();
    }

    @Override
    public void save(HealthProvider healthProvider) throws WrongCredentials {
        if(healthProviderList.contains(healthProvider)) {
            update(healthProvider);
            return;
        }
        healthProviderList.add(healthProvider);
    }

    @Override
    public HealthProvider getById(int id) {
        return healthProviderList.stream().filter(healthProvider -> healthProvider.getId() == id).findFirst().orElseGet(()-> null);
    }


    @Override
    public HealthProvider deleteById(int id) throws WrongCredentials {
        HealthProvider healthProvider = healthProviderList.stream().filter(h -> h.getId() == id).findFirst().orElseGet(()-> null);
        if(healthProvider == null) {
            throw new WrongCredentials("HealthProvider with id " + id + " does not exist");
        }
        healthProviderList.remove(healthProvider);
        return healthProvider;
    }

    @Override
    public void update(HealthProvider healthProvider) throws WrongCredentials {
        HealthProvider prevHealthProvider = healthProviderList.stream().filter(h -> h.getId() == healthProvider.getId()).findFirst().orElseGet(()-> null);
        if(prevHealthProvider == null) {
            throw new WrongCredentials("HealthProvider with id " + healthProvider.getId() + " does not exist");
        }
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

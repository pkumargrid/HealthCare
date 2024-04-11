package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface HealthProviderRepository {

    void save(HealthProvider healthProvider) throws WrongCredentials;

    HealthProvider getById(int id);

    HealthProvider deleteById(int id) throws WrongCredentials;

    List<HealthProvider> getByName(String name);

    void update(HealthProvider healthProvider) throws WrongCredentials;

    List<HealthProvider> findAll();

}

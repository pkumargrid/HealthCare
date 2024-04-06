package com.healthcare.system.services;

import com.healthcare.system.entities.HealthProvider;

import java.util.List;

public interface HealthProviderService {
    void save(HealthProvider healthProvider);

    HealthProvider getById(int id);

    HealthProvider deleteById(int id);

    List<HealthProvider> getByName(String name);

    void update(HealthProvider healthProvider);

    List<HealthProvider> findAll();
}
